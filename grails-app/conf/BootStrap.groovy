import adicionales.AsteriskManagerService

class BootStrap {

    AsteriskManagerService asteriskManagerService

    def init = { servletContext ->
        asteriskManagerService.initialize()
    }
    def destroy = {
    }
}
