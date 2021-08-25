$habiles = $("#habiles");
$inhabilitar = $("#inhabilitar");
$habilitar = $("#habilitar");
$noHabiles = $("#noHabiles");
$inhabilitar.click(function (e) {
    if(!$habiles.val()){
        alert("No ha seleccionado bases para inhabilitar");
        e.preventDefault();
        return false
    }
    $inhabilitar.val("Espere...");
    $bases = $habiles.val().toString();
    $.post(baseUrl + "/FuncionesAjax/inhabilitarBases", {bases: $bases}, function(data){
        location.reload();
    });
});

$habilitar.click(function () {
    if(!$noHabiles.val()){
        alert("No ha seleccionado bases para habilitar");
        e.preventDefault();
        return false
    }
    $habilitar.val("Espere...");
    $bases = $noHabiles.val().toString();
    $.post(baseUrl + "/FuncionesAjax/habilitarBases", {bases: $bases}, function(data){
        location.reload();
    });
});