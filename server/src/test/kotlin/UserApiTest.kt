import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.example.project.main
import org.example.project.model.User
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

@MyTmsAnnotation("C00101")
class UserApiKotestTest {
    // C00102
    @Test
    fun testCreateUserWithSuccess() =
        testApplication {
            application {
                main()
            }
            val client = createClient {
                install(ContentNegotiation) {
                    json()  // kotlinx-serialization-json 을 이용한 직렬화
                }
            }
            val response: HttpResponse = client.post("/api/register") {
                contentType(ContentType.Application.Json)
                setBody(User(userId = 0, username = "test", password = "pass", createdAt = LocalDateTime.now()))
            }
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)
        }

}
