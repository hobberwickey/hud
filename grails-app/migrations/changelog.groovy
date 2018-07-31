databaseChangeLog = {
    include file: 'init.groovy'
    include file: 'add_ordering_to_menus.groovy'
    include file: 'create_user.groovy'
    include file: 'update_dining_halls.groovy'
    include file: 'reorganize_meals_and_options.groovy'
    include file: 'remove_type_from_group_options.groovy'
    include file: 'fix_menu_items_options.groovy'
    include file: 'create_menu_sections.groovy'
    include file: 'remove_menu_items_from_menus.groovy'
    include file: 'fix_menu_meals.groovy'
    include file: 'add_menu_items_to_menu_sections.groovy'
    include file: 'persisting_local_id.groovy'
    include file: 'all_null_local_id.groovy'
    include file: 'change_ordering_to_text.groovy'
    include file: 'create_orders_and_menu_selections.groovy'
    include file: 'fix_orders.groovy'
    include file: 'add_dining_halls_to_menus.groovy'
    include file: 'fix_menu_dining_halls.groovy'
    include file: 'abstract_pickup_times.groovy'
    include file: 'rename_order_to_orders.groovy'
    include file: 'fix_orders_sql.groovy'
    include file: 'update_positioning.groovy'
    include file: 'fix_user_relations.groovy'
}
