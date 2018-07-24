<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h1><span>Menus</span> <a href="/admin/menus/new"><i class='fa fa-plus' alt='Add Menu'></i></a></h2>
    <div class='content'>
      <ul class='content-header menu-header'>
        <li><label>Name</label></li
        ><li></li>
      </ul>

      <ul class='admin-list menus'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='location-editor.js' />
  <script>
    $(function(){
      var menuEditor = new ItemEditor();
      
      menuEditor.refreshMenus().then(function(){
        menuEditor.buildMenus();
      }.bind(this));
    })
  </script>
</body>
</html>
