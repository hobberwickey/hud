<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h1><span>Locations</span> <a href="/admin/locations/new"><i class='fa fa-plus' alt='Add Location'></i></a></h2>
    <div class='content'>
      <ul class='content-header location-header'>
        <li><label>Name</label></li
        ><li><label>Semester Start</label></li
        ><li><label>Semester End</label></li
        ><li><label>Menus</label></li
        ><li><label>Status</label></li
        ><li></li>
      </ul>

      <ul class='locations admin-list'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='location-editor.js' />
  <script>
    $(function(){
      var locationEditor = new LocationEditor();
      
      locationEditor.refreshLocations().then(function(){
        locationEditor.buildLocations();
      }.bind(this));
    })
  </script>
</body>
</html>
