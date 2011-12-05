<lumen:application preload="lumennote" template="lumennote:template" implements="lumennote:noteData">
	<lumen:attribute name="message" type="String"/>
	
	<lumen:handler event="lumennote:noteAdded" action="{!c.finish}" />
	
	<div>
		{!v.message}
		
		<lumennote:details lumen:id="detail" url="{!v.url}" image="{!v.image}" text="{!v.text}"/>
	</div>
</lumen:application>
