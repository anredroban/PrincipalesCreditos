$(document).ready(function(){
    $telefonoContactadoDiv = $("#telefonoContactadoDiv");
    $telefonoContactado = $("#telefonoContactado");
    $numeroCasaDomicilio = $("#numeroCasaDomicilio");
    $telefonoContacto = $("#telefonoContacto");
    $status = $("#status")
    $subSubStatus = $("#subSubStatus");
    $divMotivo = $("#divMotivo");
    init();

    //Cuando cambia el ESTADO
    /* $("#status").change(function(){
     esconderCamposEstados();
     //   $("#telefonoContactadoDiv").hide();
     //   $("#telefonoContactadoDiv").val("");
     //$("#btnAdicional").attr("disabled", true);
     if ($("#status").val() == "") {
     emptySelect('substatus');
     }else{
     $statusId = this.value;
     if($statusId == 1){
     $telefonoContactadoDiv.show();
     }
     $.get(baseUrl + "/FuncionesAjax/getSubStatusByStatus", {
     id: $statusId
     }, function (data) {
     fillSelect('substatus', data)
     });
     if($statusId == 1){
     $telefonoContactadoDiv.show();
     }
     }
     });*/

    $status.change(function(){
        esconderCamposEstados();
        //$telefonoContactadoDiv.hide();
        // $telefonoContactado.val("");
        if($status.val() == ""){
            emptySelect('substatus');
            emptySelect('estadoTelefonoContactado');
        }else{
            $statusId = this.value;
            if($statusId == 1){
          //      $telefonoContactadoDiv.show();
            }
            $.get(baseUrl + "/FuncionesAjax/getSubStatusByStatus", {
                id: $statusId
            }, function (data) {
                fillSelect('substatus', data);
            });
            $.get(baseUrl + "/FuncionesAjax/getEstadosTelefono", {
                id: $statusId
            }, function (data) {
                fillSelect('estadoTelefonoContactado', data);
            });
        }
    });



    //Cuando cambia el SUBESTADO
    $("#substatus").change(function () {
        esconderCamposEstados();
        // $("#btnAdicional").attr("disabled", true);
        if($("#substatus").val() == ""){
            emptySelect("subSubStatus");
        }else{
            $subStatusId = this.value;
            $.get(baseUrl + "/FuncionesAjax/getSubSubStatusBySubStatus", {
                id: $subStatusId
            }, function (data) {
                if(fillSelect('subSubStatus', data) > 0){
                    $("#subSubStatusDiv").show();
                    //  $telefonoContactadoDiv.show();
                }else{
                    $("#subSubStatusDiv").hide();
                    //$("#send").show()
                }
                if(data[2] == 'Rellamada'){
                    $("#recallDiv").show();
                }else{
                    $("#recallDiv").hide();
                }
            });
        }
    });


    //Cuando cambia el SUBESTADO
    $("#subSubStatus").change(function () {
        // $("#btnAdicional").attr("disabled", true);
        if($("#subSubStatus").val() == ""){
          //  emptySelect("subSubStatus");
        }else{
            $subSubStatusId = this.value;
            $.get(baseUrl + "/FuncionesAjax/getExtraeSubSubStatus", {
                id: $subSubStatusId
            }, function (data) {
                if(data == 'CREDITO'){
                    $("#tipoAsistenciaDiv").show();
                    $("#divPDP").hide();
                }
                if(data == 'TDC'){
                    $("#managementData").show();
                    $("#divPDP").hide();
                }
                if(data == 'TDC+CREDITO'){
                    $("#managementData").show();
                    $("#tipoAsistenciaDiv").show();
                    $("#divPDP").hide();
                }
                if(data == 'CREDITO+PDP'){
                    $("#tipoAsistenciaDiv").show();
                    $("#divPDP").show();
                }
                if(data == 'PDP'){
                    $("#divPDP").show();
                    $("#managementData").hide();
                    $("#tipoAsistenciaDiv").hide();
                }
                if(data == 'EXCESO DE LIQUIDEZ'){
                    $divMotivo.show();
                }else{
                    $divMotivo.hide();
                }
            });
        }
    });


    $("#signatureDocuments").change(function () {

        if($(this).val() == "AGENCIA"){
            $("#agenciaGroup").show();
            $("#oficinaTarjeta").val($("#oficinaTarjeta option:first").val());
        }else{
            $("#agenciaGroup").hide();
            $("#oficinaTarjeta").val($("#oficinaTarjeta option:first").val());
        }
        if($(this).val() == "SIN AGENCIA"){
            $("#divFechaDesembolso").show();
            $("#oficinaTarjeta").val($("#oficinaTarjeta option:first").val());
        }else{
            $("#divFechaDesembolso").hide();
        }

        if($(this).val() == "DOMICILIO"){
            $("#groupDomicilio").show();
            //$("#oficinaTarjeta").val($("#oficinaTarjeta option:first").val());
        }else{
            $("#groupDomicilio").hide();
        }

        if($(this).val() == "DIGITAL"){
            $("#groupDigital").show();
            //$("#oficinaTarjeta").val($("#oficinaTarjeta option:first").val());
        }else{
            $("#groupDigital").hide();
        }

    });

    //Cuando cambia el SUBSUBESTADO
    $("#subSubStatus").change(function () {
        //esconderCamposEstados();
        if($("#subSubStatus").val() == ""){
            emptySelect("motivosSubSubEstados");
        }else{
            $motivosubStatusId = this.value;
            $.get(baseUrl + "/FuncionesAjax/geMotivoSubStatusBySubSubStatus", {
                id: $motivosubStatusId
            }, function (data) {
                if(fillSelect('motivosSubSubEstados', data) > 0){
                    $("#motivosSubSubEstadosDiv").show();
                }else{
                    $("#motivosSubSubEstadosDiv").hide();
                }
            });
        }
    });

    $("#motivosSubSubEstados").change(function () {

        if ($("#motivosSubSubEstados").val() == 14){
            $("#divDeseaNuevoProducto").show();
        }else {
            $("#divDeseaNuevoProducto").hide();
            $("#deseaNuevoProducto").val("");
        }
    });


    //Cuando cambia el check PROTECCION FRAUDES
    $("#fraudes").change(function () {
        $("#cobroProteccionFraudes").val($("#cobroProteccionFraudes option:first").val());
        if($(this).prop('checked')){
            $("#cobroDiv").show();
        }else
            $("#cobroDiv").hide();
    });


    //Cuando se escribe la cédula
    $('#cedula').keyup(function(){
        if($(this).val().trim().length == 10){
            var query = $(this).val();
            if(query != ''){
                load_data(query);

            }else{
                load_data();
            }
        }
    });


    //Cuando se GUARDA EL ADICIONAL
    $("#btnGuardarAdicional").click(function (e) {
        if(!validateAdicionalData()){
            e.preventDefault();
            return false;
        }
        $idCliente = $("#idCliente").val().trim();
        $cedula = $("#cedula").val().trim();
        $primerNombre = $("#primerNombre").val().trim();
        $segundoNombre = $("#segundoNombre").val().trim();
        $primerApellido = $("#primerApellido").val().trim();
        $segundoApellido = $("#segundoApellido").val().trim();
        $nombreTarjeta = $('input:radio[name=nombreTarjeta]:checked').val();
        $cupoOtorgado = $("#cupoOtorgado").val().trim();
        $sexo = $("#sexo").val().trim();
        $parentesco = $("#parentesco").val().trim();
        $fechaNacimiento = $("#fechaNacimiento").val().trim();
        $estadoCivil = $("#estadoCivilAdicional").val().trim();
        $nacionalidad = $("#nacionalidadAdicional").val().trim();
        $.get(baseUrl + "/FuncionesAjax/addAdicionalToList",
            {idCliente: $idCliente, cedula: $cedula, primerNombre: $primerNombre, segundoNombre: $segundoNombre, primerApellido: $primerApellido, segundoApellido: $segundoApellido,
                nombreTarjeta: $nombreTarjeta, cupoOtorgado: $cupoOtorgado, sexo: $sexo, parentesco: $parentesco,
                fechaNacimiento: $fechaNacimiento, estadoCivil: $estadoCivil, nacionalidad: $nacionalidad},
            function(data){
                cleanAdicionalData();
                if(data == 'true'){
                    alert("Adicional agregado");
                    //$("#send").show();
                }
            });
    });


    //Cuando sale de foco el SEGUNDO NOMBRE
    $("#segundoNombre").focusout(function () {
        if($("#segundoNombre").val().trim() != ''){
            $("#radioSegundoNombre").prop('disabled', false);
        }else {
            $("#radioPrimerNombre").prop('checked', true);
            $("#radioSegundoNombre").prop('disabled', true);
        }
    });

    //Cuando se cambia la PROVINCIA DE DOMICILIO
    $("#provinciaDomicilioGestion").change(function (data) {
        emptySelect("ciudadDomicilioGestion");
        emptySelect("cantonDomicilio");
        emptySelect("parroquiaDomicilio");
        if($("#provinciaDomicilioGestion").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudades", {id: $idProvincia}, function (data) {
                fillSelect("ciudadDomicilioGestion", data)
            });
            $.get(baseUrl + "/FuncionesAjax/getCantones", {id: $idProvincia}, function (data) {
                fillSelect("cantonDomicilio", data)
            })
        }
    });

    //Cuando se cambia la PROVINCIA DE NACIMIENTO
    $("#provinciaNacimiento").change(function (data) {
        emptySelect("ciudadNacimiento");
        //emptySelect("cantonDomicilio");
        //emptySelect("parroquiaDomicilio");
        if($("#provinciaNacimiento").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudades", {id: $idProvincia}, function (data) {
                fillSelect("ciudadNacimiento", data)
            });
        }
    });

    $("#provinciaNacimientoCU2").change(function (data) {
        emptySelect("ciudadNacimientoCU2");
        if($("#provinciaNacimientoCU2").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudades", {id: $idProvincia}, function (data) {
                fillSelect("ciudadNacimientoCU2", data)
            });
        }
    });

    //Cuando se cambia la PROVINCIA DE ENTREGA
    $("#provinciaEntrega").change(function (data) {
        emptySelect("ciudadEntrega");
        //emptySelect("cantonDomicilio");
        //emptySelect("parroquiaDomicilio");
        if($("#provinciaEntrega").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudadesEntrega", {id: $idProvincia}, function (data) {
                fillSelect("ciudadEntrega", data)
            });
        }
    });

    //Cuando se cambia la PROVINCIA DE FVT
    $("#provinciaDomicilioFvt").change(function (data) {
        emptySelect("ciudadDomicilioFvt");
        if($("#provinciaDomicilioFvt").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudadesFvt", {id: $idProvincia}, function (data) {
                fillSelect("ciudadDomicilioFvt", data)
            });
        }
    });

    //Cuando se cambia la CIUDAD DE ENTREGA
    $("#ciudadEntrega").change(function (data) {
        emptySelect("oficinaTarjeta");
        if($("#ciudadEntrega").val() != ""){
            $idCiudad = this.value;
            $.get(baseUrl + "/FuncionesAjax/getParroquiasAgencias", {id: $idCiudad}, function (data) {
                fillSelect("oficinaTarjeta", data)
            });
        }
    });
    //Cuando se cambia la PARROQUIA DE ENTREGA
   /* $("#parroquiaEntrega").change(function (data) {
        emptySelect("oficinaTarjeta");
        if($("#parroquiaEntrega").val() != ""){
            $idParroquia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getAgencias", {id: $idParroquia}, function (data) {
                fillSelect("oficinaTarjeta", data)
            });
        }
    });*/

    //Cuando se cambia el CANTON DE DOMICILIO
    $("#cantonDomicilio").change(function (data) {
        emptySelect("parroquiaDomicilio");
        if($("#cantonDomicilio").val() != ""){
            $idCanton = this.value;
            $.get(baseUrl + "/FuncionesAjax/getParroquias", {id: $idCanton}, function (data) {
                fillSelect("parroquiaDomicilio", data)
            });
        }
    });

    //Cuando se cambia la PROVINCIA DE TRABAJO
    $("#provinciaTrabajoGestion").change(function (data) {
        emptySelect("ciudadTrabajoGestion");
        emptySelect("cantonTrabajo");
        emptySelect("parroquiaTrabajo");
        if($("#provinciaTrabajoGestion").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudades", {id: $idProvincia}, function (data) {
                fillSelect("ciudadTrabajoGestion", data)
            });
            $.get(baseUrl + "/FuncionesAjax/getCantones", {id: $idProvincia}, function (data) {
                fillSelect("cantonTrabajo", data)
            });
        }
    });

    //Cuando se cambia el CANTON DE TRABAJO
    $("#cantonTrabajo").change(function (data) {
        emptySelect("parroquiaTrabajo");
        if($("#cantonTrabajo").val() != ""){
            $idCanton = this.value;
            $.get(baseUrl + "/FuncionesAjax/getParroquias", {id: $idCanton}, function (data) {
                fillSelect("parroquiaTrabajo", data)
            });
        }
    });
    //Cuando se cambia la PROVINCIA DE REFERENCIAS
    $("#provincia").change(function (data) {
        emptySelect("ciudad");
        //emptySelect("cantonDomicilio");
        //emptySelect("parroquiaDomicilio");
        if($("#provincia").val() != ""){
            $idProvincia = this.value;
            $.get(baseUrl + "/FuncionesAjax/getCiudades", {id: $idProvincia}, function (data) {
                fillSelect("ciudad", data)
            });
           /* $.get(baseUrl + "/FuncionesAjax/getCantones", {id: $idProvincia}, function (data) {
                fillSelect("ciudad", data)
            })*/
        }
    });


    //Cuando cambia el select TIPO CLIENTE




    //Cuando se quiere GUARDAR LA GESTION
    $("#send").click(function (e) {
        if(!validateManagementData()){
            e.preventDefault()
            return false
        }else{
            $("#send").hide();
        }
    });

});


//Validación de los DATOS DE GESTION
function validateManagementData() {
    $isValid = true

    if($("#priodidadTc").text() == "CLIENTE EXITOSO NO GESTIONAR"){
        alert("EL CLIENTE ACTUAL SE ENCUENTRA GUARDADO COMO EXITOSO, NO SE PUEDE CONTINUAR.");
        $isValid = false;
        return
    }

    if($("#status").val() == ""){
        alert("Debe escoger un estado");
        $isValid = false;
        return
    }else{
        if($("#substatus").val() == ""){
            alert("Debe escoger un subestado");
            $isValid = false
            return
        }else{
            if($("#recall").is(":visible")){
                if($("#agendamiento").val() == ""){
                    alert("Campo agendamiento vacio")
                    $isValid = false;
                    return
                }
                if($("#recall").val() == ""){
                    alert("Debe ingresar una fecha para la rellamada")
                    $isValid = false;
                    return
                }else{
                    if (calcularDias($("#recall").val()) > 3){
                        alert("La fecha de rellamada no puede sobrepasar los tres días")
                        $isValid = false;
                        return
                    }
                }
            }
            if($("#subSubStatus").is(":visible")){
                if($("#subSubStatus").val() == ""){
                    alert("Debe escoger un sub subestado")
                    $isValid = false;
                    return
                }
            }
            if($divMotivo.is(":visible")){
                if($("#motivoNoAceptaSeguro").val() == ""){
                    alert("Debe escoger el motivo no desea seguro")
                    $isValid = false;
                    return
                }
            }

            if($("#motivosSubSubEstadosDiv").is(":visible")){
                if($("#motivosSubSubEstados").val() == ""){
                    alert("Debe escoger el Producto que desea el cliente");
                    $isValid = false;
                    return
                }
                if($("#divDeseaNuevoProducto").is(":visible")){
                    if($("#deseaNuevoProducto").val() == ""){
                        alert("Ingrese el nuevo producto que desea el cliente.");
                        $isValid = false;
                        return;
                    }
                }
            }

            if ($telefonoContactadoDiv.is(":visible")) {
                if ($telefonoContactado.val() === "") {
                    alert("Ingrese el teléfono al cual pudo contactar al cliente");
                    $isValid = false;
                    return
                } else {
                    if ($telefonoContactado.val().substring(0, 1) != 0) {
                        alert("El teléfono contactado es incorrecto");
                        $isValid = false;
                        return
                    } else {
                        if (!validarSiNumero($telefonoContactado.val())) {
                            alert("El teléfono contactado no es un número válido");
                            $isValid = false;
                            return
                        }else{
                            if ($telefonoContactado.val().length ==  9 && $telefonoContactado.val().trim().substring(0, 2) == "09" ) {
                                alert("Esta ingresando un telefono convencional incorrecto. Verifique!");
                                $isValid = false;
                                return
                            }else {
                                if ($telefonoContactado.val().length ==  10 && $telefonoContactado.val().trim().substring(0, 2) != "09" ) {
                                    alert("Esta ingresando un telefono celular incorrecto. Verifique!");
                                    $isValid = false;
                                    return
                                }
                            }
                        }
                    }
                }
                if ($("#estadoTelefonoContactado").val() === "") {
                    alert("Ingrese el estado del teléfono contactado");
                    $isValid = false;
                    return
                }
            }

            if($("#tipoAsistenciaDiv").is(":visible")){
                /*if(!$("#exequial").prop('checked') && !$("#fraudes").prop('checked')){
                 alert("Escoja un tipo de asistencia");
                 $isValid = false;
                 return
                 }*/
                if($("#creditoAceptado").val() == ""){
                    alert("Ingrese un valor en el campo crédito aceptado")
                    $isValid = false;
                    return
                }else{
                    /*id="labelConsumoEscenario1"*/
                    if($("#labelConsumoExclusivo").text() != "" && $("#creditoAceptado").val() == "OPT1" && $("#subSubStatus").val() == 4){
                        alert("Si es solo crédito debe seleccionar Opcion 2 en el campo Crédito aceptado")
                        $isValid = false;
                        return
                    }else{
                        if($("#labelConsumoEscenario1").text() != "" && $("#creditoAceptado").val() == "OPT2" && $("#subSubStatus").val() == 43) {
                            alert("Si es TDC+CREDITO debe seleccionar la Opcion 1 en el campo Crédito aceptado")
                            $isValid = false;
                            return
                        }else{
                            if($("#labelConsumoExclusivo").text() != "" && $("#creditoAceptado").val() == "OPT1" && $("#subSubStatus").val() == 50) {
                                alert("Si es CREDITO+PDP debe seleccionar la Opcion 2 en el campo Crédito aceptado")
                                $isValid = false;
                                return
                            }
                        }
                    }
                }
                if($("#situacionLaboralCredito").val() == ""){
                    alert("Ingrese un valor en el campo Situacion Laboral de crédito")
                    $isValid = false;
                    return
                }
                if($("#signatureDocuments").val() == ""){
                    alert("Ingrese un valor en el campo Firma Documentos")
                    $isValid = false;
                    return
                }
                if($("#agenciaGroup").is(":visible")){
                    if($("#oficinaTarjeta").val() == ""){
                        alert("Debe seleccionar una agencia")
                        $isValid = false;
                        return
                    }
                    if($("#fechaAgendamiento").val() == ""){
                        alert("Debe ingresar la fecha de desembolso")
                        $isValid = false;
                        return
                    }
                    if($("#telefonoConvencionalCredito").val() == ""){
                        alert("Debe ingresar un número convencional del cliente")
                        $isValid = false;
                        return
                    }else{
                        if($("#telefonoConvencionalCredito").val().substring(0, 1) != 0){
                            alert("El teléfono convencional es incorrecto");
                            $isValid = false;
                            return
                        }else{
                            if(!validarSiNumero($("#telefonoConvencionalCredito").val())){
                                alert("El teléfono convencional no es un número válido");
                                $isValid = false;
                                return
                            }else{
                                if($("#telefonoConvencionalCredito").val().length != 9){
                                    alert("El teléfono convencional debe contener 9 dígitos");
                                    $isValid = false;
                                    return
                                }
                            }
                        }
                    }
                    if($("#telefonoCelularCredito").val() == ""){
                        alert("Debe ingresar un número celular del cliente")
                        $isValid = false;
                        return
                    }else{
                        if($("#telefonoCelularCredito").val().substring(0, 2) != 09){
                            alert("El teléfono celular es incorrecto");
                            $isValid = false;
                            return
                        }else{
                            if(!validarSiNumero($("#telefonoCelularCredito").val())){
                                alert("El teléfono celular no es un número válido");
                                $isValid = false;
                                return
                            }else{
                                if($("#telefonoCelularCredito").val().length != 10){
                                    alert("El teléfono convencional debe contener 9 dígitos");
                                    $isValid = false;
                                    return
                                }
                            }
                        }
                    }
                }
                if($("#divFechaDesembolso").is(":visible")){
                    if($("#fechaAgendamiento2").val() == ""){
                        alert("Debe ingresar la fecha de desembolso")
                        $isValid = false;
                        return
                    }
                }
                if($("#correoCredito").val() == ""){
                    alert("Debe ingresar el correo electrónico del cliente")
                    $isValid = false;
                    return
                }
            }
        }
    }

    if($("#managementData").is(":visible")){

        if($("#cedulaTitular").val() == ""){
            alert("Campo Cédula Titular vacío");
            $isValid = false;
            return;
        }else{
            if($("#cedulaTitular").val().length > 10){
                alert("La cédula puede tener hasta 10 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#nombre1").val() == ""){
            alert("Campo Nombre 1 vacío");
            $isValid = false;
            return;
        }else{
            if($("#nombre1").val().length > 20){
                alert("Nombre 1 puede tener hasta 20 caracteres");
                $isValid = false;
                return;
            }
        }

            if($("#nombre2").val().length > 20){
                alert("Nombre 2 puede tener hasta 20 caracteres");
                $isValid = false;
                return;
            }



        if($("#apellido1").val() == ""){
            alert("Campo Apellido 1 vacío");
            $isValid = false;
            return;
        }else{
            if($("#apellido1").val().length > 20){
                alert("Apellido 1 puede tener hasta 20 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#apellido2").val().length > 20){
            alert("Apellido 2 puede tener hasta 20 caracteres");
            $isValid = false;
            return;
        }

        if($("#nacionalidad").val() == ""){
            alert("Campo Nacionalidad vacío");
            $isValid = false;
            return;
        }

        if($("#genero").val() == ""){
            alert("Campo Género vacío");
            $isValid = false;
            return;
        }

        if($("#estadoCivilGestion").val() == ""){
            alert("Campo Estado Civil vacío");
            $isValid = false;
            return;
        }

        if($("#paisNacimiento").val() == ""){
            alert("Campo País Nacimiento vacío");
            $isValid = false;
            return;
        }

        if($("#provinciaNacimiento").val() == ""){
            alert("Campo Provincia Nacimiento vacío");
            $isValid = false;
            return;
        }

        if($("#ciudadNacimiento").val() == ""){
            alert("Campo Ciudad Nacimiento vacío");
            $isValid = false;
            return;
        }

        if($("#fechaNacimientoGestion").val() == ""){
            alert("Campo Fecha Nacimiento vacío");
            $isValid = false;
            return;
        }else{
            if(calcularEdad($("#fechaNacimientoGestion").val()) < 21 || calcularEdad($("#fechaNacimientoGestion").val()) > 75){
                alert("El rango de edad de la persona debe estar entre los 21 a 63 años de edad. Verifique la fecha de Nacimiento");
                $isValid = false;
                return;
            }
        }

        if($("#provinciaDomicilioGestion").val() == ""){
            alert("Campo Provincia Domicilio vacío");
            $isValid = false;
            return;
        }

        if($("#ciudadDomicilioGestion").val() == ""){
            alert("Campo Ciudad Domicilio vacío");
            $isValid = false;
            return;
        }

        if($("#cantonDomicilio").val() == ""){
            alert("Campo Cantón Domicilio vacío");
            $isValid = false;
            return;
        }
        if($("#parroquiaDomicilio").val() == ""){
            alert("Campo Parroquia vacío");
            $isValid = false;
            return;
        }
        if($("#sectorDomicilio").val() == ""){
            alert("Campo Sector de Domicilio vacio");
            $isValid = false;
            return;
        }else{
            if($("#sectorDomicilio").val().length > 20){
                alert("Sector de Domicilio puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#callePrincipalDomicilio").val() == ""){
            alert("Campo Calle Principal Domicilio vacío");
            $isValid = false;
            return;
        }else{
            if($("#callePrincipalDomicilio").val().length > 50){
                alert("Calle Principal Domicilio puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }
        if($numeroCasaDomicilio.val() == "" ){
            alert("Campo Número Casa Domicilio vacío. Ingrese SN en caso de no tener datos.");
            $isValid = false;
            return;
        }else{
            if($("#numeroCasaDomicilio").val().length > 10 ){
                alert("Número Casa Domicilio puede tener hasta 8 caracteres");
                $isValid = false;
                return;
            }
        }
        /*else{
            if($numeroCasaDomicilio.val() == " "){
                alert("Campo Número Casa Domicilio no puede tener espacios en blanco. Ingrese SN en caso de no tener datos.");
                $isValid = false;
                return;
            }
        }*/

        if($("#calleSecundariaDomicilio").val() == ""){
            alert("Campo Calle Secundaria Domicilio vacío");
            $isValid = false;
            return;
        }else{
            if($("#calleSecundariaDomicilio").val().length > 50){
                alert("Calle Secundaria Domicilio puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#referenciaDomicilio").val() == ""){
            alert("Campo Referencia Domicilio vacío");
            $isValid = false;
            return;
        }else{
            if($("#referenciaDomicilio").val().length > 250){
                alert("Referencia Domicilio puede tener hasta 250 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#telefonoDomicilio").val() == ""){
            alert("Campo Teléfono Domicilio vacío");
            $isValid = false;
            return;
        }else{
            if($("#telefonoDomicilio").val().length != 8){
                alert("El teléfono de domicilio debe tener 8 caracteres");
                $isValid = false;
                return;
            }
            else{
                if(!validarVacio($("#telefonoDomicilio").val())){
                    alert("El teléfono de domicilio no debe contener espacios en blanco");
                    $isValid = false;
                    return;
                }
                else{
                    if($("#telefonoDomicilio").val().charAt(0) == 0){
                        alert("Valor inválido para el teléfono de domicilio");
                        $isValid = false;
                        return;
                    }
                }
            }
        }
        if($("#celularGestion").val() == ""){
            alert("Campo Celular vacío");
            $isValid = false;
            return;
        }else{
            if($("#celularGestion").val().length != 10){
                alert("El celular debe tener 10 dígitos");
                $isValid = false;
                return;
            }else{
                if($("#celularGestion").val().charAt(0) != 0){
                    alert("Valor inválido para el celular");
                    $isValid = false;
                    return;
                }
                else{
                    if(!validarVacio($("#celularGestion").val())){
                        alert("El celular no debe contener espacios en blanco");
                        $isValid = false;
                        return;
                    }
                }
            }
        }
        if($("#emailPersonal").val() == ""){
            alert("Campo Email Personal vacío");
            $isValid = false;
            return;
        }else{
            if(!validarEmail($("#emailPersonal").val())){
                alert("El Email Personal ingresado es incorrecto.");
                $isValid = false;
                return;
            }else{
                if($("#emailPersonal").val().length > 50){
                    alert("El longitud de Email es inválida");
                    $isValid = false;
                    return;
                }
            }
        }
        if($("#provinciaTrabajoGestion").val() == ""){
            alert("Campo Provincia Trabajo vacío");
            $isValid = false;
            return;
        }

        if($("#ciudadTrabajoGestion").val() == ""){
            alert("Campo Ciudad Trabajo vacío");
            $isValid = false;
            return;
        }

        if($("#cantonTrabajo").val() == ""){
            alert("Campo Cantón Trabajo vacío");
            $isValid = false;
            return;
        }

        if($("#parroquiaTrabajo").val() == ""){
            alert("Campo Parroquia Trabajo vacío");
            $isValid = false;
            return;
        }
        if($("#sectorTrabajo").val() == ""){
            alert("Campo Sector Trabajo vacío");
            $isValid = false;
            return;
        }else{
            if($("#sectorTrabajo").val().length > 50){
                alert("Sector de Trabajo puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }
        if($("#callePrincipalTrabajo").val() == ""){
            alert("Campo Calle Principal Trabajo vacío");
            $isValid = false;
            return;
        }else{
            if($("#callePrincipalTrabajo").val().length > 50){
                alert("Calle Principal Trabajo puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#numeroCasaTrabajo").val() == ""){
            alert("Campo Número Casa Trabajo vacío. Ingrese SN en caso de no tener datos.");
            $isValid = false;
            return;
        }else{
            if($("#numeroCasaTrabajo").val().length > 10){
                alert("Número Casa Trabajo puede tener hasta 10 caracteres");
                $isValid = false;
                return;
            }
        }
        /*else{
            if($("#numeroCasaTrabajo").val() == " "){
                alert("Campo Número Casa Trabajo no puede tener espacios en blanco. Ingrese SN en caso de no tener datos.");
                $isValid = false;
                return;
            }
        }*/
        if($("#calleSecundariaTrabajo").val() == ""){
            alert("Campo Calle Secundaria Trabajo vacío");
            $isValid = false;
            return;
        }else{
            if($("#calleSecundariaTrabajo").val().length > 50){
                alert("Calle Secundaria Trabajo puede tener hasta 50 caracteres");
                $isValid = false;
                return;
            }
        }

        if($("#referenciaTrabajo").val() == ""){
            alert("Campo Referencia Trabajo vacío");
            $isValid = false;
            return;
        }else{
            if($("#referenciaTrabajo").val().length > 250){
                alert("Referencia Trabajo puede tener hasta 250 caracteres");
                $isValid = false;
                return;
            }
        }
        if($("#telefonoTrabajo").val() == ""){
            alert("Campo Teléfono Trabajo vacío");
            $isValid = false;
            return;
        }else{
            if($("#telefonoTrabajo").val().length != 8){
                alert("El teléfono de trabajo debe tener 8 caracteres");
                $isValid = false;
                return;
            }else{
                if(!validarVacio($("#telefonoTrabajo").val())){
                    alert("El teléfono de trabajo no debe contener espacios en blanco");
                    $isValid = false;
                    return;
                }
                else{
                    if($("#telefonoTrabajo").val().charAt(0) == 0){
                        alert("Valor inválido para el teléfono de trabajo");
                        $isValid = false;
                        return;
                    }
                }
            }
        }
        if($("#lugarEntrega").val() == ""){
            alert("Campo Lugar de Entrega vacío");
            $isValid = false;
            return;
        }

        if($("#telefonoContacto").val() == ""){
            alert("Campo Teléfono Contacto vacío");
            $isValid = false;
            return;
        }else{
            if($("#telefonoContacto").val().length == 9){
                alert("El numero de teléfono de contacto debe tener 8 o 10 dígitos");
                $isValid = false;
                return;
            }
        }
        if($("#horaContacto").val() == ""){
            alert("Campo Hora de Contacto vacío");
            $isValid = false;
            return;
        }

        if($("#nombreRecibeTarjeta").val() == ""){
            alert("Campo Nombre Recibe Tarjeta vacío");
            $isValid = false;
            return;
        }

        if($("#horarioEntrega").val() == ""){
            alert("Campo Horario de Entrega vacío");
            $isValid = false;
            return;
        }

        if($("#nombresReferencia").val() == ""){
            alert("Campo nombres de Referencia vacío");
            $isValid = false;
            return;
        }
        if($("#provincia").val() == ""){
            alert("Campo provinvia de Referencia vacío");
            $isValid = false;
            return;
        }
        if($("#ciudad").val() == ""){
            alert("Campo ciudad de Referencia vacío");
            $isValid = false;
            return;
        }
        if($("#telefono").val() == ""){
            alert("Campo telefono de Referencia vacío");
            $isValid = false;
            return;
        }else{
            if($("#telefono").val().length == 9){
                alert("Campo telefono de Referencia dete tener 8 o 10 dígitos");
                $isValid = false;
                return;
            }
        }
        if($("#relacionConCliente").val() == ""){
            alert("Campo Relación con cliente Referencia vacío");
            $isValid = false;
            return;
        }

        if($("#tipoCliente").val() == ""){
            alert("Campo Tipo Cliente vacío");
            $isValid = false;
            return;
        }else {
            if ($("#tipoCliente").val() == "I") {
                if ($("#nombreNegocio").val() == "") {
                    alert("Campo Nombre Negocio vacío");
                    $isValid = false;
                    return;
                }
                if ($("#fechaInicioNegocio").val() == "") {
                    alert("Campo Fecha Inicio Negocio vacío");
                    $isValid = false;
                    return;
                }else{
                    if (validarFecha($("#fechaInicioNegocio").val()) == false){
                        alert("El formato de fecha para el campo Fecha Inicio Negocio es AAAA/MM/DD");
                        $isValid = false;
                        return;
                    }

                }
                if ($("#actividadEconomica").val() == "") {
                    alert("Campo Actividad Económica vacío");
                    $isValid = false;
                    return;
                }
                if ($("#ventasHonorariosMensuales").val() == "") {
                    alert("Campo Honorarios Mensuales vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#ventasHonorariosMensuales").val())) {
                        alert("El campo Ventas u honorarios mensuales no es un número válido");
                        $isValid = false;
                        return
                    }
                }
                if ($("#costoVentas").val() == "") {
                    alert("Campo Costo Ventas vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#costoVentas").val())) {
                        alert("El campo Costo Ventas no es un número válido");
                        $isValid = false;
                        return
                    }
                }
                if ($("#gastosOperativos").val() == "") {
                    alert("Campo Gastos Operativos vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#gastosOperativos").val())) {
                        alert("El campo Gastos Operativos no es un número válido");
                        $isValid = false;
                        return
                    }
                }
            }
            if ($("#tipoCliente").val() == "D") {
                if ($("#situacionLaboral").val() == "") {
                    alert("Campo Situación Laboral vacío");
                    $isValid = false;
                    return;
                }
                if ($("#nombreEmpresaGestion").val() == "") {
                    alert("Campo Nombre Empresa vacío");
                    $isValid = false;
                    return;
                }
                if ($("#contrato").val() == "") {
                    alert("Campo Contrato vacío");
                    $isValid = false;
                    return;
                }
                if ($("#cargo").val() == "") {
                    alert("Campo Cargo vacío");
                    $isValid = false;
                    return;
                }
                if ($("#fechaInicioTrabajoActual").val() == "") {
                    alert("Campo Fecha Inicio Trabajo vacío");
                    $isValid = false;
                    return;
                }else{
                    if(validarFecha($("#fechaInicioTrabajoActual").val()) == false){
                        alert("El formato de fecha para el campo Fecha Inicio Trabajo es AAAA/MM/DD");
                        $isValid = false;
                        return;
                    }
                }
                if ($("#sueldo").val() == "") {
                    alert("Campo Sueldo vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#sueldo").val())) {
                        alert("El campo sueldo no es un número válido");
                        $isValid = false;
                        return
                    }
                }
                if ($("#gastosFamilia").val() == "") {
                    alert("Campo Gastos Familia vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#gastosFamilia").val())) {
                        alert("El campo gastos familia no es un número válido");
                        $isValid = false;
                        return
                    }
                }
            }
            /*PARA CLIENTES JUBILADOS*/

            if ($("#tipoCliente").val() == "J") {
                if ($("#situacionLaboralJubilado").val() == "") {
                    alert("Campo Situación Laboral Jubilado vacío");
                    $isValid = false;
                    return;
                }
                if ($("#nombreEmpresaGestionJubilado").val() == "") {
                    alert("Campo Nombre Empresa Jubilado vacío");
                    $isValid = false;
                    return;
                }
                if ($("#contratoJubilado").val() == "") {
                    alert("Campo Contrato Jubilado vacío");
                    $isValid = false;
                    return;
                }
                if ($("#cargoJubilado").val() == "") {
                    alert("Campo Cargo Jubilado vacío");
                    $isValid = false;
                    return;
                }
                if ($("#fechaInicioTrabajoActualJubilado").val() == "") {
                    alert("Campo Fecha Inicio Jubilación vacío");
                    $isValid = false;
                    return;
                }else{
                    if(validarFecha($("#fechaInicioTrabajoActualJubilado").val()) == false){
                        alert("El formato de fecha para el campo Fecha Inicio Trabajo Jubilado es AAAA/MM/DD");
                        $isValid = false;
                        return;
                    }
                }
                if ($("#sueldoJubilado").val() == "") {
                    alert("Campo Sueldo Jubilación vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#sueldoJubilado").val())) {
                        alert("El campo sueldo no es un número válido");
                        $isValid = false;
                        return
                    }
                }
                if ($("#gastosFamiliaJubilado").val() == "") {
                    alert("Campo Gastos Familia Jubilado vacío");
                    $isValid = false;
                    return;
                } else {
                    if (!validarSiNumero($("#gastosFamiliaJubilado").val())) {
                        alert("El campo gastos familia no es un número válido");
                        $isValid = false;
                        return
                    }
                }
            }
        }

        if($("#tipoVivienda").val() == ""){
            alert("Campo tipo Vivienda vacío");
            $isValid = false;
            return;
        }

        if($("#obligadoContabilidad").val() == ""){
            alert("Campo Obligado Contabilidad vacío");
            $isValid = false;
            return;
        }

        /*   if($("#opcionTarjetaSeleccionada").val() == ""){
         alert("Campo Tarjeta Seleccionada vacío");
         $isValid = false;
         return;
         }*/

        if($("#estadoCuentaDigital").val() == ""){
            alert("Campo Estado de Cuenta Digital vacío");
            $isValid = false;
            return;
        }
        if($("#sms").val() == ""){
            alert("Campo Desea SMS vacío");
            $isValid = false;
            return;
        }
        if($("#seguroDesgravamen").val() == ""){
            alert("Campo Seguro Desgravamen vacío");
            $isValid = false;
            return;
        }
    }

    if($("#divPDP").is(":visible")){
        if($("#provinciaNacimientoCU2").val() == ""){
            alert("Campo provincia PDP vacío");
            $isValid = false;
            return;
        }
        if($("#ciudadNacimientoCU2").val() == ""){
            alert("Campo Ciudad PDP vacío");
            $isValid = false;
            return;
        }
    }


    if($("#vinculoConfianza").val() == ""){
        alert("Campo Vínculo Confianza vacío");
        $isValid = false;
        return;
    }
    if($("#contratoPreliminar").val() == ""){
        alert("Campo Contrato Preliminar vacío");
        $isValid = false;
        return;
    }
/*
    //Se valida que la información concatenada no exceda los 150 caracteres
    $informacionConcatenadaDomicilio = $("#sectorDomicilio").val() + " " + $("#callePrincipalDomicilio").val() + " " + $("#numeroCasaDomicilio").val()+" " + $("#calleSecundariaDomicilio").val() + " " + $("#referenciaDomicilio").val();
    $informacionConcatenadaTrabajo = $("#sectorTrabajo").val() + " " + $("#callePrincipalTrabajo").val() + " " + $("#numeroCasaTrabajo").val()+" " + $("#calleSecundariaTrabajo").val() + " " + $("#referenciaTrabajo").val();
    $longitudDomicilio = $informacionConcatenadaDomicilio.length;
    $longitudTrabajo = $informacionConcatenadaTrabajo.length
    if($longitudDomicilio > 95){
        alert("La longitud en total de la dirección de domicilio tiene "+$longitudDomicilio+" caracteres. Sólo se permite hasta 95");
        $isValid = false;
        return;
    }

    if($longitudTrabajo > 95){
        alert("La longitud en total de la dirección de trabajo tiene "+$longitudTrabajo+" caracteres. Sólo se permite hasta 95");
        $isValid = false;
        return;
    }*/

    return $isValid;
}


//Para cuando se INICIA LA GESTION
function init() {
    esconderCamposEstados();
   // $("#telefonoContactadoDiv").hide();
    $("#status").val($("#status option:first").val());
    $("#sexo").val($("#sexo option:first").val());
    $("#estadoCivil").val($("#estadoCivil option:first").val());
    $("#parentesco").val($("#parentesco option:first").val());
    $("#provinciaDomicilioGestion").val($("#estadoCivil option:first").val());
    $("#provinciaTrabajoGestion").val($("#estadoCivil option:first").val());
    $("#radioPrimerNombre").prop('checked', true);
    $("#radioSegundoNombre").prop('disabled', true);
    $("#tipoCliente").val($("#tipoCliente option:first").val());
    $("#dataIndependiente").hide();
    $("#dataDependiente").hide();
    $("#dataJubilado").hide();
    $("#divPDP").hide();
    $("#divFechaDesembolso").hide();
    $("#groupDomicilio").hide();
    $("#groupDigital").hide();
    $("#divDeseaNuevoProducto").hide();
    $("#motivosSubSubEstadosDiv").hide();
    $("#deseaNuevoProducto").val("");
    $("#btnAdicional").attr("disabled", true);
}

//This function empties a select component
function emptySelect(idSelect) {

    var select = document.getElementById(idSelect);
    var option = document.createElement('option');
    var NumberItems = select.length;

    while (NumberItems > 0) {
        NumberItems--;
        select.remove(NumberItems);
    }

    option.text = '-- Seleccione --';
    option.value = ''
    select.add(option, null);
}

//This function fills a select component
function fillSelect(idSelect, data) {

    var select = document.getElementById(idSelect);
    var numberSubstatus = data[0].length;

    emptySelect(idSelect)

    if (numberSubstatus > 0) {
        for (i = 0; i < numberSubstatus; i++) {
            var option = document.createElement('option');
            option.text = data[1][i];
            option.value = data[0][i];
            select.add(option, null);
        }
    }

    return numberSubstatus;
}

//Validación de la información de los ADICIONALES
function validateAdicionalData() {
    $isValid = true;
    if($("#cedula").val().trim() == ''){
        alert("Valor inválido para campo cédula");
        $isValid = false;
        return;
    }else{
        if($("#cedula").val().trim().length != 10){
            alert("La cédula debe tener 10 dígitos");
            $isValid = false;
            return;
        }else{
            if($("#cedula").val().trim() == $("#identificacion").val().trim()){
                alert("El número  de cédula del Adicional no debe ser igual al del Principal");
                $isValid = false;
                return;
            }
        }
    }
    if($("#primerNombre").val().trim() == ''){
        alert("Debe ingresar el primer nombre");
        $isValid = false;
        return;
    }
    if($("#primerApellido").val().trim() == ''){
        alert("Debe ingresar el primer apellido");
        $isValid = false;
        return;
    }
    if($("#cupoOtorgado").val().trim() == ''){
        alert("Ingrese el cupo otorgado");
        $isValid = false;
        return;
    }
    if($("#sexo").val() == ''){
        alert("Ingrese el sexo del Adicional");
        $isValid = false;
        return;
    }
    if($("#parentesco").val() == ''){
        alert("Ingrese el parentesco del Adicional");
        $isValid = false;
        return;
    }
    if($("#fechaNacimiento").val() == ''){
        alert("Ingrese la fecha de nacimiento del Adicional");
        $isValid = false;
        return;
    }
    if($("#estadoCivilAdicional").val() == ''){
        alert("Ingrese el estado civil del Adicional");
        $isValid = false;
        return;
    }
    if($("#nacionalidadAdicional").val() == ''){
        alert("Ingrese la nacionalidad del Adicional");
        $isValid = false;
        return;
    }
    return $isValid;
}


$('#horaContacto').on('keypress', function(e) {
    if(checkIfNumberNoSpace(e.which, e)==0){
        return false;
    }else{
        return;
    }
});

/**
 *@description Funcion que evita que puedan ingresar numeros en campos
 * @author Andres Redroban
 * */

function checkIfNumberNoSpace(codeKey,e){
    if (codeKey == 32)
        return 0;
    // Asignando numero y no espacios
    if ($.inArray(codeKey, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
        // Allow: Ctrl+A
        (codeKey == 97 && e.ctrlKey === true) ||
        // Allow: Ctrl+C
        (codeKey == 99 && e.ctrlKey === true) ||
        // Allow: Ctrl+X
        (codeKey == 120 && e.ctrlKey === true) ||
        // Allow: home, end, left, right, tab
        (codeKey == 0)) {
        // let it happen, don't do anything
        return 1;
    }
    if ((codeKey < 48 || codeKey > 57)) {
        return 0;
    }
}
function soloLetras(e){
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = "8-37-39-46";

    tecla_especial = false
    for(var i in especiales){
        if(key == especiales[i]){
            tecla_especial = true;
            break;
        }
    }

    if(letras.indexOf(tecla)==-1 && !tecla_especial){
        return false;
    }
}


/**
 * Valida si el valor ingresado es numérico
 * @param numero
 */
function validarSiNumero(numero){
    $esNumero = true;
    if (!/^([0-9])*$/.test(numero)){
        $esNumero = false;
    }
    return $esNumero;
}
/**
 * Valida si el correo ingresado es correcto
 * @param email
 * @author Andres Redroban
 */
function validarEmail(email)
{
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email) ? true : false;
}

function validarSiSoloLetras(entrada){
    $esSoloTexto = true;
    $filtro = /^([A-Za-z ])*$/;
    if(!$filtro.test(entrada)){
        $esSoloTexto = false;
    }
    return $esSoloTexto;
}

/**
 * Valida si el numero ingresado contiene espacios en blanco
 * @param validar
 * @author Andres Redroban
 */
function validarVacio(validar) {
    $esVacio = true;
    for ( i = 0; i < validar.length; i++ ) {
        if ( validar.charAt(i) == " " ) {
            $esVacio = false
        }
    }
    return $esVacio
}

/**
 * Función bajada de internet https://es.stackoverflow.com/questions/31373/obtener-la-edad-a-partir-de-la-fecha-de-nacimiento-con-javascript-y-php
 * @param fecha
 * @returns {number}
 */
function calcularEdad(fecha) {
    var hoy = new Date();
    var cumpleanos = new Date(fecha);
    var edad = hoy.getFullYear() - cumpleanos.getFullYear();
    var m = hoy.getMonth() - cumpleanos.getMonth();

    if (m < 0 || (m === 0 && hoy.getDate() < cumpleanos.getDate())) {
        edad--;
    }

    return edad;
}
/**
 * Función tomada como ejemplo de internet https://www.lawebdelprogramador.com/foros/JavaScript/1594805-Calcular-la-cantidad-de-dias-entre-dos-fechas-Javascript-y-HTML.html
 * @param fecha
 * @returns {contdias}
 * @author Andres Redrobán
 * @description La siguiente función calcula el numero de dias tomando como referencia el facha actual y la fecha ingresada desde el sistema.
 */
function calcularDias(fecha){
    var fechaini = new Date();
    var fechafin = new Date(fecha);
    var diasdif= fechafin.getTime()-fechaini.getTime();
    var contdias = Math.round(diasdif/(1000*60*60*24));
    return contdias;
}

/**
 * Funcion para validar una fecha en formato YYYY-MM-DD
 * @param fecha
 * @return {boolean}
 * @author Andres Redrobán
 */

function validarFecha(fecha){
    var fechaArr = fecha.split('/');
    var anio = fechaArr[0];
    var mes = fechaArr[1];
    var dia = fechaArr[2];

    var plantilla = new Date(anio, mes - 1, dia);//mes empieza de cero Enero = 0

    if(!plantilla || plantilla.getFullYear() == anio && plantilla.getMonth() == mes -1 && plantilla.getDate() == dia){
        return true;
    }else{
        return false;
    }
}

//Para limpiar luego de agregar un adicional
function cleanAdicionalData() {
    $("#cedula").val("");
    $("#primerNombre").val("");
    $("#segundoNombre").val("");
    $("#primerApellido").val("");
    $("#segundoApellido").val("");
    $("#radioPrimerNombre").prop('checked', true);
    $("#radioSegundoNombre").prop('disabled', true);
    $("#cupoOtorgado").val("");
    $("#sexo").val($("#sexo option:first").val());
    $("#parentesco").val($("#parentesco option:first").val());
    $("#fechaNacimiento").val("");
    $("#observaciones").val("");
    $("#estadoCivilAdicional").val($("#estadoCivilAdicional option:first").val());
    $("#nacionalidadAdicional").val("");
}

function load_data(query){
    $.post(baseUrl + "/FuncionesAjax/searchUser", {
        query:query
    }).done(function (data) {

        //$('#result').html(data);
        if(data == 'null'){

        }else{
            alert('ya tienes este usuario en la base de datos con la misma cedula');
        }
    });
}

function esconderCamposEstados(){
    $("#motivoNoDesea").val($("#motivoNoDesea option:first").val());
    $("#cobroProteccionFraudes").val($("#cobroProteccionFraudes option:first").val());
    $("#recallDiv").hide();
    $("#subSubStatusDiv").hide();
    $("#managementData").hide();
    //$("#send").hide();
    $("#motNoDeseaDiv").hide();
    $("#tipoAsistenciaDiv").hide();
    $("#exequial").prop('checked', false);
    $("#fraudes").prop('checked', false);
    $("#cobroDiv").hide();
    $("#agenciaGroup").hide();
    $("#divPDP").hide();
    $("#divDeseaNuevoProducto").hide();
    $("#motivosSubSubEstadosDiv").hide();
    $divMotivo.hide();
}

function limpiarTipoCliente(){
    $("#nombreNegocio").val("");
    $("#fechaInicioNegocio").val("");
    $("#actividadEconomica").val("");
    $("#ventasHonorariosMensuales").val("");
    $("#costoVentas").val("");
    $("#gastosOperativos").val("");
    $("#situacionLaboral").val($("#situacionLaboral option:first").val());
    $("#nombreEmpresaGestion").val("");
    $("#contrato").val($("#contrato option:first").val());
    $("#cargo").val("");
    $("#fechaInicioTrabajoActual").val("");
    $("#sueldo").val("");
    $("#gastosFamilia").val("");
}