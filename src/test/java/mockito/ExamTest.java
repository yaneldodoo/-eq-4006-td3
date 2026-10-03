package mockito;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExamTest {

    @Test
    public void shouldSendWeightedGradeToStudent() {

        // Arrange
        Student student = mock(Student.class);
        Exam exam = new Exam("exam1", student, 80.0);

        // Act
        exam.calculateGrade(0.25);

        // Assert
        verify(student).calculateExamGrade("exam1", 20.0);
    }
}