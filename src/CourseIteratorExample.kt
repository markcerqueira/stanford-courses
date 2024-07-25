import com.opencsv.CSVWriter
import edu.stanford.services.explorecourses.ExploreCoursesConnection
import org.jdom2.JDOMException
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Prints a list of all courses offered at Stanford in the current academic year
 */
object CourseIteratorExample {
    @Throws(IOException::class, JDOMException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // First create file object for file placed at location specified by filepath
        val file = File("stanford-courses-${DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now())}.csv")

        try {
            // Create FileWriter object with file as parameter
            val outputFile = FileWriter(file);

            // Create CSVWriter object FileWriter object as parameter
            val csvWriter = CSVWriter(outputFile);

            // Add header to CSV
            val header = arrayOf(
                "Subject",
                "Course",
                "Title",
                "Course Id",
                "Terms",
                "Description",
                "Tags",
                "Academic Org",
                "Academic Group",
                "Academic Career",
                "Instructors"
            )
            csvWriter.writeNext(header)

            val connection = ExploreCoursesConnection()
            for (s in connection.schools) {
                for (d in s.departments) {
                    for (c in connection.getCoursesByQuery(d.code)) {
                        val courseDataArray = arrayOf(
                            c.subjectCodePrefix,
                            c.subjectCodeSuffix,
                            c.title,
                            c.courseId.toString(),
                            c.sections.map { it.term }.toSet().joinToString(", "),
                            c.description,
                            c.tags.joinToString(", ") { it.organization + "::" + it.name },
                            c.academicOrganization,
                            c.academicGroup,
                            c.academicCareer,
                            "TODO"
                        )

                        csvWriter.writeNext(courseDataArray)

                        println(courseDataArray.joinToString(" "))
                    }
                }
            }

            csvWriter.close()
            println("All done!")
        } catch (e: IOException) {
            e.printStackTrace();
        }
    }
}
