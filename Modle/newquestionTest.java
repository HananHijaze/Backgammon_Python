//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//public class newquestionTest {
//
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	@Test
//	public void testAddNewQuestion() {
//		MangQuestionControl controller =new MangQuestionControl();
//        Questions newQuestion = new Questions("What is a key principle of Agile software development?", new String[]{"1.Extensive planning at the beginning of a project to ensure there are no changes in the requirements."
//        		,"2.Strong documentation is more important than working software.",
//        		"3.Regular adaptation to changing circumstances and user feedback.",
//        		"4.Complete each project phase before starting the next one without any overlap."}, 3, 1);
//        int initialSize = controller.getQuestions().size();
//
//        controller.addNewQuestion(newQuestion);
//
//        List<Questions> updatedQuestions = controller.getQuestions();
//        int updatedSize = updatedQuestions.size();
//
//        // The question list size should be increased by 1 and we can see it now in the JSON file.
//        Assertions.assertTrue(updatedSize == initialSize + 1);
//    }
//
//}
