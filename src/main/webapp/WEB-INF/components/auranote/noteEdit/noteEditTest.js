({
	testLocationSuccess: {
		attributes : {
			note :'title:"this is my title", body:"this is my body"'
				},
        test: function(cmp){
        	var overrideNav = $A.test.overrideFunction(navigator.geolocation, "getCurrentPosition",
                    function(success, failure) {
                       var results = {
                            coords : {
                                latitude: 67890,
                                longitude: 12345
                            }
                      
                        };
                        success(results);
                    });
        	    var expected = "12345, 67890";
                cmp.find('ui_button_set_location').get('e.press').fire();
                              
                /*
                 * This function will compare the function in its overridden form of the function to it original form 
                 * (which should be restored after restore is called). If they are not different, then the call to restore 
                 * has failed.
                 */
                var testFunction = function(){ 
	               	 var overriddenFunc = navigator.geolocation.getCurrentPosition;
	               	 
	               	 try{
	               		 overrideNav.restore();
	               	 } catch (e){
	               		 console.log("Error was thrown due to trying to restore navigator.geolocation to its original version\n"+e);
	               		 return false;
	               	 } 
	               	 
	               	 var origFunc = navigator.geolocation.getCurrentPosition
	               	 return (overriddenFunc === origFunc); 
               };
                
                $A.test.addWaitForWithFailureMessage(true, testFunction, "restoring the overridden function did not happen as planned");
                
                var actual = cmp.find("ui_button_set_location").get("v.label");
                $A.test.assertEquals(actual, expected, "Location button did not change. Expected: "+expected+" Actual: "+actual);
               
            }
        },
     testLocationFailure: {
         test: function(cmp){
         	var overrideNav = $A.test.overrideFunction(navigator.geolocation, "getCurrentPosition",
                     function(success, failure) {
                         failure({});
                     });
         	     var expected = "Failed to get location. Try Again...";     
         	
                 cmp.find('ui_button_set_location').get('e.press').fire();
                 var testFunction = function(){
                 	 var overriddenFunc = navigator.geolocation.getCurrentPosition;
                	 try{
	               		 overrideNav.restore();
	               	 } catch (e){
	               		 console.log("Error was thrown due to trying to restore navigator.geolocation to its original version\n"+e);
	               		 return false;
	               	 } 
	               	 
	               	 var origFunc = navigator.geolocation.getCurrentPosition
	               	 return (overriddenFunc === origFunc); 
                 	    
                 };
                 
                 var actual = cmp.find("ui_button_set_location").get("v.label");
                
                 $A.test.assertEquals(actual, expected, "Location button did not change. Expected: "+expected+" Actual: "+actual);
                 
                 actual = cmp.find("ui_button_set_location").get("v.disabled");
                 expected = "false";
                 
                 $A.test.assertEquals(actual, expected, "Location button is disabled and it shouldn't be");
             }
         }
})