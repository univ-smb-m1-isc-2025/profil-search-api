package usmb.info803.profile_search;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import usmb.info803.profile_search.bullet_point.BulletPoint;
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

        // Vérifier si member4 avec rousseaumaxime27@gmail.com existe déjà
        Member member4Existing = memberRepository.findByEmail("rousseaumaxime27@gmail.com");
        Member member4;
        if (member4Existing == null) {
            member4 = new Member("Rousseau", "Maxime", "rousseaumaxime27@gmail.com", usmb);
            member4 = memberRepository.save(member4);
            System.out.println("Membre créé: " + member4.getPrenom() + " " + member4.getNom() + " (" + member4.getEmail() + ")");
        } else {
            System.out.println("Membre existant trouvé: " + member4Existing.getPrenom() + " " + member4Existing.getNom());
            member4 = member4Existing;
        }

        // Créer le deuxième membre avec l'email spécifié
        Member member5 = new Member("Rousseau", "Maxime", "rousseaumaxime27000@gmail.com", google);
        member5 = memberRepository.save(member5);
        System.out.println("Membre créé: " + member5.getPrenom() + " " + member5.getNom() + " (" + member5.getEmail() + ")");

        // Créer des offres
        Offre offre1 = new Offre("Développeur Java", member1, true);
        Offre offre2 = new Offre("Ingénieur Machine Learning", member2, false);
        Offre offre3 = new Offre("Développeur Java Spring Boot", member1, false);
        Offre offre4 = new Offre("Développeur Full Stack React/Node.js", member4, true);
        Offre offre5 = new Offre("DevOps Engineer", member5, true);
        Offre offre6 = new Offre("Product Owner", member5, false);

        offre1 = offreRepository.save(offre1);
        offre2 = offreRepository.save(offre2);
        offre3 = offreRepository.save(offre3);
        offre4 = offreRepository.save(offre4);
        offre5 = offreRepository.save(offre5);
        offre6 = offreRepository.save(offre6);
        System.out.println("Offres créées: " + offre1.getTitre() + ", " + offre2.getTitre() + ", " + offre3.getTitre() + ", " + offre4.getTitre() + ", " + offre5.getTitre() + ", " + offre6.getTitre());

        // Créer des paragraphes et les associer aux offres
        Paragraphe paragraphe1 = new Paragraphe("Nous recherchons un développeur Java expérimenté pour rejoindre notre équipe.", offre1);
        Paragraphe paragraphe2 = new Paragraphe("Le candidat idéal aura une solide connaissance des frameworks Java modernes.", offre1);
        Paragraphe paragraphe3 = new Paragraphe("Nous recherchons un expert en machine learning pour travailler sur des projets innovants.", offre2);
        Paragraphe paragraphe4 = new Paragraphe("Nous recherchons un développeur Java expérimenté pour rejoindre notre équipe dynamique. Le candidat idéal aura une solide expérience en développement Spring Boot et en architecture microservices.", offre3);
        Paragraphe paragraphe5 = new Paragraphe("Nous recherchons un développeur Full Stack maîtrisant React et Node.js pour développer des applications web modernes.", offre4);
        Paragraphe paragraphe6 = new Paragraphe("Rejoignez notre équipe DevOps pour mettre en place et gérer notre infrastructure cloud.", offre5);
        Paragraphe paragraphe7 = new Paragraphe("Nous recherchons un Product Owner expérimenté pour piloter le développement de nos produits.", offre6);

        paragrapheRepository.save(paragraphe1);
        paragrapheRepository.save(paragraphe2);
        paragrapheRepository.save(paragraphe3);
        paragrapheRepository.save(paragraphe4);
        paragrapheRepository.save(paragraphe5);
        paragrapheRepository.save(paragraphe6);
        paragrapheRepository.save(paragraphe7);

        System.out.println("Paragraphes créés et associés aux offres");

        // Ajouter des bullet points aux offres
        BulletPoint bulletPoint1 = new BulletPoint("Expérience de 5 ans en Java", offre1);
        BulletPoint bulletPoint2 = new BulletPoint("Maîtrise de Spring Boot", offre1);
        BulletPoint bulletPoint3 = new BulletPoint("Connaissance approfondie de TensorFlow", offre2);
        BulletPoint bulletPoint4 = new BulletPoint("5+ ans d'expérience en développement Java", offre3);
        BulletPoint bulletPoint5 = new BulletPoint("3+ ans d'expérience en React.js", offre4);
        BulletPoint bulletPoint6 = new BulletPoint("Maîtrise de Node.js et Express", offre4);
        BulletPoint bulletPoint7 = new BulletPoint("Expérience avec Docker et Kubernetes", offre5);
        BulletPoint bulletPoint8 = new BulletPoint("Certifications Scrum/Agile", offre6);

        offre1.addBulletPoint(bulletPoint1);
        offre1.addBulletPoint(bulletPoint2);
        offre2.addBulletPoint(bulletPoint3);
        offre3.addBulletPoint(bulletPoint4);
        offre4.addBulletPoint(bulletPoint5);
        offre4.addBulletPoint(bulletPoint6);
        offre5.addBulletPoint(bulletPoint7);
        offre6.addBulletPoint(bulletPoint8);

        offreRepository.save(offre1);
        offreRepository.save(offre2);
        offreRepository.save(offre3);
        offreRepository.save(offre4);
        offreRepository.save(offre5);
        offreRepository.save(offre6);

        System.out.println("Bullet points créés pour les offres");

        // Créer des questions
        Question question1 = new Question("Quelle est votre expérience en développement Java?");
        Question question2 = new Question("Avez-vous déjà travaillé avec Spring Boot?");
        Question question3 = new Question("Quelles sont vos compétences en intelligence artificielle?");
        Question question4 = new Question("Préférez-vous travailler en équipe ou individuellement?");
        Question question5 = new Question("Quelle est votre expérience avec Spring Boot?");
        Question question6 = new Question("Quelle est votre expérience avec React.js?");
        Question question7 = new Question("Avez-vous déjà travaillé avec Docker?");
        Question question8 = new Question("Quelles méthodes agiles avez-vous déjà pratiquées?");

        question1 = questionRepository.save(question1);
        question2 = questionRepository.save(question2);
        question3 = questionRepository.save(question3);
        question4 = questionRepository.save(question4);
        question5 = questionRepository.save(question5);
        question6 = questionRepository.save(question6);
        question7 = questionRepository.save(question7);
        question8 = questionRepository.save(question8);

        System.out.println("Questions créées: " + question1.getQuestion() + ", " + question2.getQuestion() + ", etc.");

        // Associer des questions aux offres
        OffresQuestion offresQuestion1 = new OffresQuestion(offre1, question1);
        OffresQuestion offresQuestion2 = new OffresQuestion(offre1, question2);

        OffresQuestion offresQuestion3 = new OffresQuestion(offre2, question3);
        OffresQuestion offresQuestion4 = new OffresQuestion(offre2, question4);

        OffresQuestion offresQuestion5 = new OffresQuestion(offre3, question5);

        OffresQuestion offresQuestion6 = new OffresQuestion(offre4, question6);

        OffresQuestion offresQuestion7 = new OffresQuestion(offre5, question7);

        OffresQuestion offresQuestion8 = new OffresQuestion(offre6, question8);

        offresQuestionRepository.save(offresQuestion1);
        offresQuestionRepository.save(offresQuestion2);
        offresQuestionRepository.save(offresQuestion3);
        offresQuestionRepository.save(offresQuestion4);
        offresQuestionRepository.save(offresQuestion5);
        offresQuestionRepository.save(offresQuestion6);
        offresQuestionRepository.save(offresQuestion7);
        offresQuestionRepository.save(offresQuestion8);

        System.out.println("Questions associées aux offres");

        // Créer des candidatures
        usmb.info803.profile_search.candidature.Candidature candidature1 = new usmb.info803.profile_search.candidature.Candidature("candidat1@example.com", "Thomas Durand", offre1);
        usmb.info803.profile_search.candidature.Candidature candidature2 = new usmb.info803.profile_search.candidature.Candidature("candidat2@example.com", "Sophie Moreau", offre1);
        usmb.info803.profile_search.candidature.Candidature candidature3 = new usmb.info803.profile_search.candidature.Candidature("candidat3@example.com", "Lucas Martin", offre4);
        usmb.info803.profile_search.candidature.Candidature candidature4 = new usmb.info803.profile_search.candidature.Candidature("candidat4@example.com", "Emma Bernard", offre5);

        // Ajouter des états différents aux candidatures
        candidature1.setAssignee(member1);
        candidature1.setPositif(true);

        candidature2.setAssignee(member4);
        candidature2.setClosed(true);
        candidature2.setPositif(false);

        candidature3.setAssignee(member5);

        // Les sauvegarder
        candidature1 = candidatureRepository.save(candidature1);
        candidature2 = candidatureRepository.save(candidature2);
        candidature3 = candidatureRepository.save(candidature3);
        candidature4 = candidatureRepository.save(candidature4);

        System.out.println("Candidatures créées: " + candidature1.getName() + ", " + candidature2.getName() + ", " + candidature3.getName() + ", " + candidature4.getName());

        // Créer des question-réponses pour les candidatures
        QuestionReponse qr1 = new QuestionReponse(candidature1, question1, "J'ai 5 ans d'expérience en développement Java.");
        QuestionReponse qr2 = new QuestionReponse(candidature1, question2, "Oui, j'ai travaillé sur plusieurs projets avec Spring Boot.");

        QuestionReponse qr3 = new QuestionReponse(candidature2, question1, "J'ai 3 ans d'expérience en développement Java.");
        QuestionReponse qr4 = new QuestionReponse(candidature2, question2, "Oui, j'ai utilisé Spring Boot dans mes projets précédents.");

        QuestionReponse qr5 = new QuestionReponse(candidature3, question6, "J'ai 2 ans d'expérience avec React.js.");

        QuestionReponse qr6 = new QuestionReponse(candidature4, question7, "Oui, j'ai travaillé avec Docker dans mon dernier emploi.");

        qr1 = questionReponseRepository.save(qr1);
        qr2 = questionReponseRepository.save(qr2);
        qr3 = questionReponseRepository.save(qr3);
        qr4 = questionReponseRepository.save(qr4);
        qr5 = questionReponseRepository.save(qr5);
        qr6 = questionReponseRepository.save(qr6);

        System.out.println("Réponses aux questions ajoutées pour les candidatures");

        System.out.println("=== INITIALISATION TERMINÉE ===");
    }
}
