<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>Simple Markers</title>
    <style>
      #map {
        height: 100%;
      }

      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
</head>
<body>
<div id="map"></div>
<script>

      function initMap() {

        var cells = [<#list cells as cell>{lat:${cell.lat?c},lng:${cell.lon?c}}<#sep>,</#list>]
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: {lat:24.936904907227,lng:55.228958129883}
        });
        for(cell of cells)
        var marker = new google.maps.Marker({
          position: cell,
          map: map
        });
      }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?callback=initMap">
</script>
</body>
</html>