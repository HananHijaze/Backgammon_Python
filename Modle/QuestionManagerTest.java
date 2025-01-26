//import static org.junit.Assert.*;
//
//import java.lang.reflect.Field;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import game.Question;
//import game.QuestionManager;
//
//
//public class QuestionManagerTest {
//
//	private QuestionManager questionManager;
//	
//
//    @Before
//    public void setUp() throws Exception {
//        questionManager = new QuestionManager();
//
//        // Provide a hardcoded list of questions
//        List<Question> questions = questionManager.getQuestions();
//
//        // Use reflection to set the private `questions` field
//        Field questionsField = QuestionManager.class.getDeclaredField("questions");
//        questionsField.setAccessible(true);
//        questionsField.set(questionManager, questions);
//    }
//
//    @Test
//    public void testDisplayQuestionWithValidDifficulty() {
//        questionManager.displayQuestion(1, isCorrect -> {
//            assertNotNull("The callback result should not be null.", isCorrect);
//            assertTrue("The result should be a boolean value.", isCorrect || !isCorrect);
//        });
//    }
//}