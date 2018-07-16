databaseChangeLog = {
    include file: 'init.groovy'
    include file: 'add_ordering_to_menus.groovy'
    include file: 'create_user.groovy'
    include file: 'update_dining_halls.groovy'
    include file: 'reorganize_meals_and_options.groovy'
    include file: 'remove_type_from_group_options.groovy'
    include file: 'fix_menu_items_options.groovy'
    include file: 'create_menu_sections.groovy'
}
