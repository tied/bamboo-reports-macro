	var updateArtifactLinksURL = function(event) {
		console.log('Updating URL');
		if (!$('#macro-param-override').prop('checked')){
		    $('#macro-param-url').val(
				$('#macro-param-serverurl').val() +
				'/rest/api/latest/result/' +
				$('#macro-param-project').val() +
				'-' +
				$('#macro-param-plan').val() +
				'.json?expand=results[:' +
				($('#macro-param-count').val() - 1) +
				'].result.stages.stage.results.result.artifacts'
		    );
		    console.log('Updated URL');
		}
	};
	
	var toggleURLDisabled = function() {
		console.log("Toggling URL");
		var urlField = $('#macro-param-url');
		urlField.prop("disabled", !urlField.prop("disabled"));
	};
	
	AJS.MacroBrowser.setMacroJsOverride('bamboo-artifact-links',{
    	fields: {
    		"string": function(param){
    			var field = AJS.MacroBrowser.ParameterFields["string"](param, {onchange:updateArtifactLinksURL});
    			if (param.name === "url"){
    				$(field.input).prop("disabled", true);
    			}
    			return field;
    		},
    		"boolean": function(param){
    			return AJS.MacroBrowser.ParameterFields["boolean"](param, {onchange:toggleURLDisabled});
    		}
        }
    });
    
    