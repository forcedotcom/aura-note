package configuration;
import org.auraframework.demo.converters.StringToNoteConverter;
import org.auraframework.demo.notes.Note;
import org.auraframework.util.ServiceLoaderImpl.AuraConfiguration;
import org.auraframework.util.ServiceLoaderImpl.Impl;
import org.auraframework.util.type.Converter;

@AuraConfiguration
public class NoteConverterConfig {
	
	   @Impl
	    public static Converter<String, Note> exampleTypeConverter() {
	        return new StringToNoteConverter();
	    }
	
}
