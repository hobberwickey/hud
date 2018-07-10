<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>HUD Users</title>
</head>
<body>
  <div class='admin'>
    <h2>Users <i className='fa fa-plus' alt='Add User'></i></h2>
    <div class='content'>
      <ul class='user-header'>
        <li><label>HUID</label></li>
        <li><label>Name</label></li>
        <li><label>Type</label></li>
        <li><label>Status</label></li>
      </ul>
      <ul class='users'>
        
      </ul> 
    </div>
  </div>

  <asset:javascript src='user-editor.js' />
  <script>
    $(function(){
      var userEditor = new UserEditor();
    })
  </script>
</body>
</html>
