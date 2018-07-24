<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>HUD Users</title>
</head>
<body>
  <div class='admin'>
    <h2>Add a User</h2>
    <div class='content'>
      <div class='user-form-wrapper'>
        
      </div> 
    </div>
  </div>

  <asset:javascript src='user-editor.js' />
  <script>
    $(function(){
      var userEditor = new UserEditor(),
          user_id = parseInt(window.location.pathname.split("/")[3], 10);
      
      Promise.all([
        userEditor.getLocations(),
        userEditor.getUser(user_id)
      ]).then(function(){
        console.log(userEditor.user)

        userEditor.buildUserForm(userEditor.user);
      });
    });
  </script>
</body>
</html>
