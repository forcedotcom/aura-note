<plume:application preload="plumenote" template="plumenote:template" implements="plumenote:noteData">
	<plume:attribute name="message" type="String"/>
	
	<plume:handler event="plumenote:noteAdded" action="{!c.finish}" />
	
	<div>
		{!v.message}
		
		<plumenote:details plume:id="detail" url="{!v.url}" image="{!v.image}" text="{!v.text}"/>
	</div>
</plume:application>
