package usmb.info803.profile_search;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import usmb.info803.profile_search.bullet_point.BulletPoint;
import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.candidature.CandidatureRepository;
import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseRepository;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberRepository;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreRepository;
import usmb.info803.profile_search.offres_question.OffresQuestion;
import usmb.info803.profile_search.offres_question.OffresQuestionRepository;
import usmb.info803.profile_search.paragraphe.Paragraphe;
import usmb.info803.profile_search.paragraphe.ParagrapheRepository;
import usmb.info803.profile_search.question.Question;
import usmb.info803.profile_search.question.QuestionRepository;
import usmb.info803.profile_search.question_reponse.QuestionReponse;
import usmb.info803.profile_search.question_reponse.QuestionReponseRepository;

@Service
public class Initializer {

    private final EntrepriseRepository entrepriseRepository;
    private final MemberRepository memberRepository;
    private final OffreRepository offreRepository;
    private final QuestionRepository questionRepository;
    private final ParagrapheRepository paragrapheRepository;
    private final OffresQuestionRepository offresQuestionRepository;
    private final CandidatureRepository candidatureRepository;
    private final QuestionReponseRepository questionReponseRepository;

    public Initializer(
            EntrepriseRepository entrepriseRepository,
            MemberRepository memberRepository,
            OffreRepository offreRepository,
            QuestionRepository questionRepository,
            ParagrapheRepository paragrapheRepository,
            OffresQuestionRepository offresQuestionRepository,
            CandidatureRepository candidatureRepository,
            QuestionReponseRepository questionReponseRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.memberRepository = memberRepository;
        this.offreRepository = offreRepository;
        this.questionRepository = questionRepository;
        this.paragrapheRepository = paragrapheRepository;
        this.offresQuestionRepository = offresQuestionRepository;
        this.candidatureRepository = candidatureRepository;
        this.questionReponseRepository = questionReponseRepository;
    }

    @PostConstruct
    public void init() {
        // Nettoyer les données existantes
        questionReponseRepository.deleteAllInBatch();
        candidatureRepository.deleteAllInBatch();
        offresQuestionRepository.deleteAllInBatch();
        paragrapheRepository.deleteAllInBatch();
        offreRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        entrepriseRepository.deleteAllInBatch();
        questionRepository.deleteAllInBatch();

        System.out.println("=== INITIALISATION DES DONNÉES ===");

        // Créer des entreprises
        Entreprise usmb = new Entreprise("USMB");
        Entreprise google = new Entreprise("Google");
        Entreprise apple = new Entreprise("Apple");

        usmb = entrepriseRepository.save(usmb);
        google = entrepriseRepository.save(google);
        apple = entrepriseRepository.save(apple);
        System.out.println("Entreprises créées: USMB, Google, Apple");

        // Créer des membres
        Member member1 = new Member("Dupont", "Jean", "jean.dupont@usmb.fr", usmb);
        Member member2 = new Member("Martin", "Sophie", "sophie.martin@google.com", google);
        Member member3 = new Member("Dubois", "Pierre", "pierre.dubois@apple.com", apple);

        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);

        System.out.println("Membres créés: " + member1.getPrenom() + ", " + member2.getPrenom() + ", " + member3.getPrenom());

        // Créer des offres
        Offre offre1 = new Offre("Développeur Java", member1, true);
        Offre offre2 = new Offre("Ingénieur Machine Learning", member2, false);
        Offre offre3 = new Offre("Développeur Java Spring Boot", member1, false);

        offre1 = offreRepository.save(offre1);
        offre2 = offreRepository.save(offre2);
        offre3 = offreRepository.save(offre3);
        System.out.println("Offres créées: " + offre1.getTitre() + ", " + offre2.getTitre() + ", " + offre3.getTitre());

        // Créer des paragraphes et les associer aux offres
        Paragraphe paragraphe1 = new Paragraphe("Nous recherchons un développeur Java expérimenté pour rejoindre notre équipe.", offre1);
        Paragraphe paragraphe2 = new Paragraphe("Le candidat idéal aura une solide connaissance des frameworks Java modernes.", offre1);
        Paragraphe paragraphe3 = new Paragraphe("Nous recherchons un expert en machine learning pour travailler sur des projets innovants.", offre2);
        Paragraphe paragraphe4 = new Paragraphe("Nous recherchons un développeur Java expérimenté pour rejoindre notre équipe dynamique. Le candidat idéal aura une solide expérience en développement Spring Boot et en architecture microservices.", offre3);

        paragrapheRepository.save(paragraphe1);
        paragrapheRepository.save(paragraphe2);
        paragrapheRepository.save(paragraphe3);
        paragrapheRepository.save(paragraphe4);

        System.out.println("Paragraphes créés et associés aux offres");

        // Ajouter des bullet points aux offres
        BulletPoint bulletPoint1 = new BulletPoint("Expérience de 5 ans en Java", offre1);
        BulletPoint bulletPoint2 = new BulletPoint("Maîtrise de Spring Boot", offre1);
        BulletPoint bulletPoint3 = new BulletPoint("Connaissance approfondie de TensorFlow", offre2);
        BulletPoint bulletPoint4 = new BulletPoint("5+ ans d'expérience en développement Java", offre3);

        offre1.addBulletPoint(bulletPoint1);
        offre1.addBulletPoint(bulletPoint2);
        offre2.addBulletPoint(bulletPoint3);
        offre3.addBulletPoint(bulletPoint4);

        offreRepository.save(offre1);
        offreRepository.save(offre2);
        offreRepository.save(offre3);

        System.out.println("Bullet points créés pour les offres");

        // Créer des questions
        Question question1 = new Question("Quelle est votre expérience en développement Java?");
        Question question2 = new Question("Avez-vous déjà travaillé avec Spring Boot?");
        Question question3 = new Question("Quelles sont vos compétences en intelligence artificielle?");
        Question question4 = new Question("Préférez-vous travailler en équipe ou individuellement?");
        Question question5 = new Question("Quelle est votre expérience avec Spring Boot?");

        question1 = questionRepository.save(question1);
        question2 = questionRepository.save(question2);
        question3 = questionRepository.save(question3);
        question4 = questionRepository.save(question4);
        question5 = questionRepository.save(question5);

        System.out.println("Questions créées: " + question1.getQuestion() + ", " + question2.getQuestion() + ", etc.");

        // Associer des questions aux offres
        OffresQuestion offresQuestion1 = new OffresQuestion(offre1, question1);
        OffresQuestion offresQuestion2 = new OffresQuestion(offre1, question2);
        OffresQuestion offresQuestion3 = new OffresQuestion(offre2, question3);
        OffresQuestion offresQuestion4 = new OffresQuestion(offre2, question4);
        OffresQuestion offresQuestion5 = new OffresQuestion(offre3, question5);

        offresQuestionRepository.save(offresQuestion1);
        offresQuestionRepository.save(offresQuestion2);
        offresQuestionRepository.save(offresQuestion3);
        offresQuestionRepository.save(offresQuestion4);
        offresQuestionRepository.save(offresQuestion5);

        System.out.println("Questions associées aux offres");

        // Créer des candidatures correctement selon le modèle Candidature.java
        Candidature candidature1 = new Candidature("john.smith@gmail.com", "John Smith", offre1);
        candidature1.setAssignee(member1);

        Candidature candidature2 = new Candidature("emma.johnson@yahoo.com", "Emma Johnson", offre2);
        candidature2.setAssignee(member2);

        candidature1 = candidatureRepository.save(candidature1);
        candidature2 = candidatureRepository.save(candidature2);

        System.out.println("Candidatures créées pour John Smith et Emma Johnson");

        // Créer des réponses aux questions
        QuestionReponse reponse1 = new QuestionReponse(candidature1, question1, "J'ai 4 ans d'expérience en développement Java, principalement sur des applications d'entreprise.");
        QuestionReponse reponse2 = new QuestionReponse(candidature1, question2, "Oui, j'ai travaillé avec Spring Boot pendant 2 ans sur plusieurs projets.");
        QuestionReponse reponse3 = new QuestionReponse(candidature2, question3, "J'ai étudié le machine learning pendant mon master et j'ai travaillé sur plusieurs projets utilisant TensorFlow et PyTorch.");
        QuestionReponse reponse4 = new QuestionReponse(candidature2, question4, "Je m'adapte aux deux environnements, mais je préfère travailler en équipe pour les projets complexes.");

        questionReponseRepository.save(reponse1);
        questionReponseRepository.save(reponse2);
        questionReponseRepository.save(reponse3);
        questionReponseRepository.save(reponse4);

        System.out.println("Réponses aux questions créées pour les candidatures");
    }
}
