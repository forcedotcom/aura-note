({
	afterRender : function(cmp){
		cmp.find("title").getElement().focus();
		this.superAfterRender();
	}
})