<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Food Stuffs</title>

  <!-- <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous"> -->
</head>
<body>
  <div class='admin'>
    <div class='content'>
      <ul class='menus'>

      </ul> 
      <!-- <div class='menu-item-admin'>
        <form class='menu-items'>
          <div class='menu-item'>
            <div class='input-wrapper name'>
              <label>Name</label>
              <input type='text' name='name' value="" placeholder="" />
            </div>
            <div class='input-wrapper admin-name'>
              <label>Admin Name [optional]</label>
              <input type='text' name='admin-name' value="" placeholder="" />
            </div>
            
            <div class='input-wrapper input-list options'>
              <label>Item Options</label>
              <ul class='item-options'>
                <li><input type='text' value='White' /><i class='far fa-times-circle' alt='Remove Option' ></i></li>
                <li><input type='text' value='Wheat' /><i class='far fa-times-circle' alt='Remove Option' ></i></li>
                <li><input type='text' value='Rye' /><i class='far fa-times-circle' alt='Remove Option' ></i></li>
              </ul>
              <div class='btn add-item-btn'>Add Option</div>
            </div>
          </div>

          <div class='btn add-item-btn'>Add Menu Item</div>
        </form>
      </div> -->
    </div>
  </div>
  <asset:javascript src='item-editor.js' />
  <script>
    $(function(){
      var itemEditor = new ItemEditor();
    })
  </script>
</body>
</html>
