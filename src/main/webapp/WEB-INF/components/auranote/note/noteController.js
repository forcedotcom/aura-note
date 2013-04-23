({
	togggleLocationImages: function(component){
		var locImages = component.find("locImages").getElement();
		$A.util.toggleClass(locImages, "show");
	}
})