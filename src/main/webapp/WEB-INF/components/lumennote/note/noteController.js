({
	edit: function(component) {
		component.getValue("v.mode").setValue("edit");
	},
	
	cancelEdit: function(component) {
		var note = component.getValue("v.note");
		note.getValue("title").rollback();
		note.getValue("body").rollback();
		component.getValue("v.mode").setValue("view");
	},
	
	togggleLocationImages: function(component){
		var locImages = component.find("locImages").getElement();
		$F.util.addClass(locImages, "show");
		$F.log(locImages)
	}
})