<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>HUD Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h1>Add a Location</h1>
    <div class='content'>
      <div class='location-form-wrapper'>
        
      </div> 
    </div>
  </div>

  <asset:javascript src='location-editor.js' />
  <script>
    $(function(){
      var locationEditor = new LocationEditor();
          location_id = parseInt(window.location.pathname.split("/")[3], 10);
        
      Promise.all([
        locationEditor.getLocation(location_id),
        locationEditor.refreshMenus()
      ]).then(function(){
        locationEditor.buildLocationForm(locationEditor.location)  
      });
    })
  </script>
</body>
</html>
