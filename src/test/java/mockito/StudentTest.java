package mockito;

import mockito.exceptions.ExamGradeAlreadyRecordedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentTest {

    @Test
    public void shouldAddExamGradeWhenExamIsNotRecorded() {

        // Arrange
        StudentRecord studentRecord = mock(StudentRecord.class);
        Student student = new Student(studentRecord);

        when(studentRecord.containsExamRecord("exam1"))
                .thenReturn(false);

        // Act
        student.calculateExamGrade("exam1", 20.0);

        // Assert
        verify(studentRecord)
                .addExamRecord("exam1", 20.0);
    }

    @Test
    public void shouldThrowWhenExamGradeIsAlreadyRecorded() {

        // Arrange
        StudentRecord studentRecord = mock(StudentRecord.class);
        Student student = new Student(studentRecord);

        when(studentRecord.containsExamRecord("exam1"))
                .thenReturn(true);

        // Act + Assert
        assertThrows(
                ExamGradeAlreadyRecordedException.class,
                () -> student.calculateExamGrade("exam1", 20.0)
        );

        verify(studentRecord, never())
                .addExamRecord("exam1", 20.0);
    }

    @Test
    public void shouldPublishFinalGrade() {

        // Arrange
        StudentRecord studentRecord = mock(StudentRecord.class);
        GradePublisher gradePublisher = mock(GradePublisher.class);

        Student student = new Student(studentRecord);

        when(studentRecord.containsExamRecord("exam1"))
                .thenReturn(false);

        when(studentRecord.containsExamRecord("exam2"))
                .thenReturn(false);

        student.calculateExamGrade("exam1", 20.0);
        student.calculateExamGrade("exam2", 30.0);

        // Act
        student.publishFinalGrade(gradePublisher);

        // Assert
        verify(gradePublisher).publish(50.0);
    }
}