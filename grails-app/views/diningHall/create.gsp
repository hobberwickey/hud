<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>HUD Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h2>Add a Location</h2>
    <div class='content'>
      <div class='location-form-wrapper'>
        
      </div> 
    </div>
  </div>

  <asset:javascript src='location-editor.js' />
  <script>
    $(function(){
      var locationEditor = new LocationEditor(),
          form = locationEditor.buildLocationForm(null);

      document.querySelector(".location-form-wrapper").appendChild(form);
    })
  </script>
</body>
</html>
