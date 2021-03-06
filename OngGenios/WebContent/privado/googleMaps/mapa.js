
var geocoder;
var map;
var marker;
 

function initialize() {

    var latlng = new google.maps.LatLng(-26.3044084, -48.8463832);
    
    //alert(latlng);

    var options = {

        zoom: 5,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };


    map = new google.maps.Map(document.getElementById("mapa"), options);
    geocoder = new google.maps.Geocoder();

        //alert(map);

    marker = new google.maps.Marker({

        map: map,
        draggable: true,
    });

    //alert(latlng);

    marker.setPosition(latlng);
}
 
$(document).ready(function () {
    initialize();

    function carregarNoMapa(endereco) {
        geocoder.geocode({ 'address': endereco + ', Brasil', 'region': 'BR' }, function (results, status) {
            if (status == google.maps.GeocoderStatus.OK) {

                if (results[0]) {

                    var latitude = results[0].geometry.location.lat();
                    var longitude = results[0].geometry.location.lng();

                 //alert( "estou dentro do meu botao" +latitude +" testes longetudeo " + longitude);

                    $('#txtEndereco').val(results[0].formatted_address);
                    $('#txtLatitude').val(latitude);
                    $('#txtLongitude').val(longitude);
                    
                    var location = new google.maps.LatLng(latitude, longitude);
                    marker.setPosition(location);
                    map.setCenter(location);
                    map.setZoom(16);
                }
            }
        }); 
    }

    $("#btnEndereco").click(function() {

        if($(this).val() != "")

            carregarNoMapa($("#txtEndereco").val());
    })
 
    $("#txtEndereco").blur(function() {

        if($(this).val() != "")

            carregarNoMapa($(this).val());
    });
    google.maps.event.addListener(marker, 'drag', function () {
        geocoder.geocode({ 'latLng': marker.getPosition() }, function (results, status) {
            //alert(results);
            if (status == google.maps.GeocoderStatus.OK) {

                    if (results[0]) { 
                    $('#txtEndereco').val(results[0].formatted_address);
                    $('#txtLatitude').val(marker.getPosition().lat());
                    $('#txtLongitude').val(marker.getPosition().lng());
                }
            }
        });
    });
     $("#txtEndereco").autocomplete({
        source: function (request, response) {
            geocoder.geocode({ 'address': request.term + ', joinville', 'sc': 'BR' }, function (results, status) {

                response($.map(results, function (item) {

                    return {

                        label: item.formatted_address,
                        value: item.formatted_address,
                        latitude: item.geometry.location.lat(),
                        longitude: item.geometry.location.lng()
                    }
                }));
            })
        },
        select: function (event, ui) {

            $("#txtLatitude").val(ui.item.latitude);
            $("#txtLongitude").val(ui.item.longitude);
            var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
            marker.setPosition(location);
            map.setCenter(location);
            map.setZoom(16);
        }
    });
});

