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
          form = userEditor.buildUserForm(null);

      console.log(form)
      document.querySelector(".user-form-wrapper").appendChild(form);
    })
  </script>
</body>
</html>
