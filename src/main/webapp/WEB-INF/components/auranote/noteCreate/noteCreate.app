<aura:application preload="auranote" template="auranote:template" implements="auranote:noteData">
	<aura:attribute name="message" type="String"/>
	
	<aura:handler event="auranote:noteAdded" action="{!c.finish}" />
	
	<div>
		{!v.message}
		
		<auranote:details aura:id="detail" url="{!v.url}" image="{!v.image}" text="{!v.text}"/>
	</div>
</aura:application>
