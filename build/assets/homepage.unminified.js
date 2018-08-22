$( document ).ready(function() {
    
  $('.toggle').on('keypress click', function() {
    $input = $( this );
    $state = $input.attr('aria-pressed');
    if ($state == "false") {
      $input.attr('aria-pressed', "true")
    } else {
      $input.attr('aria-pressed', "false")
    }
  });
}); 
