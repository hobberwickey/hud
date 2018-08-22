import grails.plugins.metadata.GrailsPlugin
import org.grails.gsp.compiler.transform.LineNumber
import org.grails.gsp.GroovyPage
import org.grails.web.taglib.*
import org.grails.taglib.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_hud_orderscreate_gsp extends org.grails.gsp.GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/orders/create.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(3)
createTagBody(1, {->
printHtmlPart(4)
expressionOut.print(meal.name)
printHtmlPart(5)
expressionOut.print(meal.name.toLowerCase())
printHtmlPart(6)
expressionOut.print(order.id == null ? '' : order.id)
printHtmlPart(7)
expressionOut.print(order.id)
printHtmlPart(8)
if(true && (errors['pickupDate'].size() > 0)) {
printHtmlPart(9)
for( err in (errors['pickupDate']) ) {
printHtmlPart(10)
expressionOut.print(err)
printHtmlPart(11)
}
printHtmlPart(12)
}
printHtmlPart(13)
for( date in (helpers.availableDates()) ) {
printHtmlPart(14)
invokeTag('radio','g',38,['name':("pickupDate"),'value':(date),'readonly':(true),'checked':(helpers.isSameDate(date, orderPickup.pickupDate))],-1)
printHtmlPart(15)
expressionOut.print(helpers.replaceParam('pickupDate', date))
printHtmlPart(16)
invokeTag('formatDate','g',40,['date':(date),'type':("date"),'style':("MEDIUM")],-1)
printHtmlPart(17)
}
printHtmlPart(18)
if(true && (errors['pickupTime'].size() > 0)) {
printHtmlPart(9)
for( err in (errors['pickupTime']) ) {
printHtmlPart(10)
expressionOut.print(err)
printHtmlPart(11)
}
printHtmlPart(12)
}
printHtmlPart(13)
for( time in (helpers.availableTimes()) ) {
printHtmlPart(19)
invokeTag('radio','g',59,['name':("pickupTime"),'value':(time),'readonly':(true),'checked':(helpers.isSameTime(time, orderPickup.pickupTime))],-1)
printHtmlPart(15)
expressionOut.print(helpers.replaceParam('pickupTime', time))
printHtmlPart(16)
invokeTag('formatDate','g',61,['date':(time),'type':("time"),'style':("SHORT")],-1)
printHtmlPart(17)
}
printHtmlPart(20)
if(true && (errors['diningHall'].size() > 0)) {
printHtmlPart(9)
for( err in (errors['diningHall']) ) {
printHtmlPart(10)
expressionOut.print(err)
printHtmlPart(11)
}
printHtmlPart(12)
}
printHtmlPart(13)
loop:{
int i = 0
for( location in (allLocations) ) {
printHtmlPart(21)
invokeTag('radio','g',82,['name':("diningHallId"),'value':(location.id),'readonly':(true),'disabled':(helpers.locationAvailable(availableLocations, location)),'checked':(order.diningHall != null && order.diningHall.id == location.id)],-1)
printHtmlPart(15)
expressionOut.print(helpers.replaceParam('diningHallId', location.id))
printHtmlPart(16)
expressionOut.print(location.name)
printHtmlPart(17)
i++
}
}
printHtmlPart(22)
invokeTag('radio','g',95,['name':("repeated"),'value':(true),'checked':(order.orderPickups.size() > 1)],-1)
printHtmlPart(23)
invokeTag('radio','g',99,['name':("repeated"),'value':(false),'checked':(order.orderPickups.size() < 2)],-1)
printHtmlPart(24)
expressionOut.print(order.orderPickups.size() > 0 ? helpers.lastPickup(order).pickupDate.format('yyyy-MM-dd') : '')
printHtmlPart(25)
expressionOut.print(order.menu == null ? '' : order.menu.id)
printHtmlPart(26)
if(true && (order.menu != null)) {
printHtmlPart(13)
for( section in (order.menu.menuSections.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(27)
if(true && (section.name == 'breakfast' && section.menuItems.size() > 0)) {
printHtmlPart(28)
if(true && (errors['breakfast'].size() > 0)) {
printHtmlPart(29)
for( err in (errors['breakfast']) ) {
printHtmlPart(30)
expressionOut.print(err)
printHtmlPart(31)
}
printHtmlPart(32)
}
printHtmlPart(33)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(34)
if(true && (helpers.hasItem(order, item))) {
printHtmlPart(35)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(37)
}
else {
printHtmlPart(35)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(38)
}
printHtmlPart(39)
expressionOut.print(item.name)
printHtmlPart(40)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(41)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(42)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(43)
if(true && (group.name == groupType)) {
printHtmlPart(44)
expressionOut.print(group.name)
printHtmlPart(45)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(46)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(47)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(48)
}
else {
printHtmlPart(47)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(49)
}
printHtmlPart(50)
expressionOut.print(opt.name)
printHtmlPart(51)
}
printHtmlPart(52)
}
printHtmlPart(42)
}
printHtmlPart(53)
}
printHtmlPart(54)
}
printHtmlPart(55)
}
printHtmlPart(56)
}
printHtmlPart(57)
if(true && (section.name == 'sandwiches-salads' && section.menuItems.size() > 0)) {
printHtmlPart(58)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(59)
if(true && (helpers.hasItem(order, item))) {
printHtmlPart(60)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(61)
}
else {
printHtmlPart(60)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(62)
}
printHtmlPart(63)
expressionOut.print(item.name)
printHtmlPart(64)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(65)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(53)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(42)
if(true && (group.name == groupType)) {
printHtmlPart(66)
expressionOut.print(group.name)
printHtmlPart(67)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(68)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(69)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(70)
}
else {
printHtmlPart(69)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
printHtmlPart(72)
expressionOut.print(opt.name)
printHtmlPart(73)
}
printHtmlPart(74)
}
printHtmlPart(53)
}
printHtmlPart(75)
}
printHtmlPart(76)
}
printHtmlPart(77)
}
printHtmlPart(78)
}
printHtmlPart(57)
if(true && (section.name == 'beverages' && section.menuItems.size() > 0)) {
printHtmlPart(79)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(59)
if(true && (helpers.hasItem(order, item))) {
printHtmlPart(60)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(61)
}
else {
printHtmlPart(60)
expressionOut.print('section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(80)
}
printHtmlPart(63)
expressionOut.print(item.name)
printHtmlPart(64)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(65)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(53)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(42)
if(true && (group.name == groupType)) {
printHtmlPart(66)
expressionOut.print(group.name)
printHtmlPart(67)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(68)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(69)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(70)
}
else {
printHtmlPart(69)
expressionOut.print('group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
printHtmlPart(72)
expressionOut.print(opt.name)
printHtmlPart(73)
}
printHtmlPart(74)
}
printHtmlPart(53)
}
printHtmlPart(75)
}
printHtmlPart(76)
}
printHtmlPart(77)
}
printHtmlPart(78)
}
printHtmlPart(57)
if(true && (section.name == 'snacks' && section.menuItems.size() > 0)) {
printHtmlPart(81)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(59)
if(true && (helpers.hasSnackItem(order, item, 1))) {
printHtmlPart(60)
expressionOut.print('snack.1.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(61)
}
else {
printHtmlPart(60)
expressionOut.print('snack.1.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(62)
}
printHtmlPart(63)
expressionOut.print(item.name)
printHtmlPart(64)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(65)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(53)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(42)
if(true && (group.name == groupType)) {
printHtmlPart(66)
expressionOut.print(group.name)
printHtmlPart(67)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(68)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(69)
expressionOut.print('snack.1.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
else {
printHtmlPart(69)
expressionOut.print('snack.1.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
printHtmlPart(72)
expressionOut.print(opt.name)
printHtmlPart(73)
}
printHtmlPart(74)
}
printHtmlPart(53)
}
printHtmlPart(75)
}
printHtmlPart(76)
}
printHtmlPart(77)
}
printHtmlPart(82)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(59)
if(true && (helpers.hasSnackItem(order, item, 2))) {
printHtmlPart(60)
expressionOut.print('snack.2.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(61)
}
else {
printHtmlPart(60)
expressionOut.print('snack.2.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(80)
}
printHtmlPart(63)
expressionOut.print(item.name)
printHtmlPart(64)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(65)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(53)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(42)
if(true && (group.name == groupType)) {
printHtmlPart(66)
expressionOut.print(group.name)
printHtmlPart(67)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(68)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(69)
expressionOut.print('snack.2.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(70)
}
else {
printHtmlPart(69)
expressionOut.print('snack.2.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
printHtmlPart(72)
expressionOut.print(opt.name)
printHtmlPart(73)
}
printHtmlPart(74)
}
printHtmlPart(53)
}
printHtmlPart(75)
}
printHtmlPart(76)
}
printHtmlPart(77)
}
printHtmlPart(83)
for( item in (section.menuItems.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(59)
if(true && (helpers.hasSnackItem(order, item, 3))) {
printHtmlPart(60)
expressionOut.print('snack.3.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(61)
}
else {
printHtmlPart(60)
expressionOut.print('snack.3.section.' + section.id + '.menuItems')
printHtmlPart(36)
expressionOut.print(item.id)
printHtmlPart(80)
}
printHtmlPart(63)
expressionOut.print(item.name)
printHtmlPart(64)
if(true && (item.menuItemOptionGroups.size() > 0)) {
printHtmlPart(65)
for( groupType in (['Dressing', 'Bread', 'Cheese']) ) {
printHtmlPart(53)
for( group in (item.menuItemOptionGroups) ) {
printHtmlPart(42)
if(true && (group.name == groupType)) {
printHtmlPart(66)
expressionOut.print(group.name)
printHtmlPart(67)
for( opt in (group.menuItemOptions.sort{a,b -> a.position.compareTo(b.position)}) ) {
printHtmlPart(68)
if(true && (helpers.hasItemOption(order, item, opt))) {
printHtmlPart(84)
expressionOut.print('snack.3.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(70)
}
else {
printHtmlPart(84)
expressionOut.print('snack.3.group.' + group.id + '.menuItem.' + item.id)
printHtmlPart(36)
expressionOut.print(opt.id)
printHtmlPart(71)
}
printHtmlPart(72)
expressionOut.print(opt.name)
printHtmlPart(73)
}
printHtmlPart(74)
}
printHtmlPart(53)
}
printHtmlPart(75)
}
printHtmlPart(76)
}
printHtmlPart(77)
}
printHtmlPart(78)
}
printHtmlPart(13)
}
printHtmlPart(85)
}
printHtmlPart(86)
expressionOut.print(meal.name.toLowerCase())
printHtmlPart(87)
})
invokeTag('captureBody','sitemesh',620,[:],1)
printHtmlPart(88)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1534914332000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'none'
public static final String TAGLIB_CODEC = 'none'
}
