<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h1><span>Users</span> <a href="/admin/users/new"><i class='fa fa-plus' alt='Add User'></i></a></h2>
    <div class='content'>
      <ul class='content-header user-header'>
        <li><label>HUID</label></li
        ><li><label>Name</label></li
        ><li><label>Type</label></li
        ><li><label>Status</label></li
        ><li></li>
      </ul>

      <ul class='admin-list users'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='user-editor.js' />
  <script>
    $(function(){
      var userEditor = new UserEditor();
      
      userEditor.getUsers(0).then(function(){
        userEditor.buildUsers();
      }.bind(this));
    })
  </script>
</body>
</html>
