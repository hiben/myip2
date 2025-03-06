import com.beust.jcommander.JCommander
import com.beust.jcommander.Parameter
import io.javalin.Javalin
import java.time.Instant

class Options {
    @Parameter(names = arrayOf("-p", "--port"), description = "port")
    var port = 8080
}

class Main {
    fun run(options : Options) {
        Javalin
            .create()
            .get("/*") { ctx ->
                var currenTime = Instant.now()
                var ip =
                    ctx.header("x-real-ip") ?:
                    ctx.header("x-forwarded-for") ?:
                    ctx.req().remoteAddr
                ctx.res().addHeader("Current-Time", currenTime.toString())
                ctx.res().addHeader("Current-Time-Unix", currenTime.toEpochMilli().toString())
                ctx.res().addHeader("My-Ip", ip)
                ctx.html("""
                    |<html><body>
                    |<h1>${ip}</h1>
                    |<b>Time: ${currenTime}</b></br>
                    ${ctx.headerMap().map { e ->
                        "|<b>${e.key}:</b> ${e.value}<br>"
                    }.joinToString("\n")}
                    |</body></html>
                    """.trimMargin()
                )
            }
            .start(options.port)
    }
}


fun main(vararg args : String) {
    var options = Options()
    JCommander.newBuilder().addObject(options).build().parse(*args)
    try {
        Main().run(options)
    } catch(e : Exception) {
        print("Unable to start server: ${e.message}")
    }
}
