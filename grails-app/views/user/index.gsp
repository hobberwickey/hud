<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Dining Halls</title>
</head>
<body>
  <div class='admin'>
    <h1><span>Users</span> <a href="/admin/users/new"><i class='fa fa-plus' alt='Add User'></i></a></h2>
    <form class='user-filters'>
      <div class='filter user-type'>
        <div class='input-wrapper radio'>
          <input type='radio' name='user-type' value='' id='user-type-1' checked />
          <label class='btn'>All</label>
        </div>
        <div class='input-wrapper radio'>
          <input type='radio' name='user-type' value='on-campus' id='user-type-1' />
          <label class='btn'>On-Campus</label>
        </div>
        <div class='input-wrapper radio'>
          <input type='radio' name='user-type' value='off-campus' id='user-type-1' />
          <label class='btn'>Off-Campus</label>
        </div>
        <div class='input-wrapper radio'>
          <input type='radio' name='user-type' value='manager' id='user-type-1' />
          <label class='btn'>Managers</label>
        </div>
        <div class='input-wrapper radio'>
          <input type='radio' name='user-type' value='admin' id='user-type-1' />
          <label class='btn'>Administators</label>
        </div>
      </div>

      <div class='input-wrapper user-name'>
        <input type='text' name='user-name' placeholder='HUID or Name' id='user_name' />
      </div>
    </form>
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

      document.getElementById("user_name").addEventListener('change', function(e){
        userEditor.filter("user_name", e.target.value)
      })

      document.getElementById("user_name").addEventListener('keypress', function(e){
        if (e.keyCode === 13){
          e.preventDefault();
          userEditor.filter("user_name", e.target.value)
        }
      })

      Array.prototype.map.call(document.querySelectorAll(".filter.user-type input"), function(input){
        input.addEventListener("change", function(e){
          userEditor.filter("user_type", e.target.value)
        })
      })
    })
  </script>
</body>
</html>
