jQuery(function ($) {
	
	var updateArtifactLinksURL = function(event) {
		console.log('Updating URL');
		if ($('#macro-param-override').prop('checked')){
		    $('#macro-param-url').val(
				$('#macro-param-serverurl').val() +
				'/rest/api/latest/result/' +
				$('#macro-param-project').val() +
				'-' +
				$('#macro-param-plan').val() +
				'.json?expand=results[:' +
				$('#macro-param-count').val() +
				'].result.stages.stage.results.result.artifacts'
		    );
		    console.log('Updated URL');
		}
	};
	
	var toggleUrlDisabled = function() {
		$('#macro-param-url').disabled = !$('#macro-param-url').disabled;
	};
	
	var initArtifactsLinkMacro  = function() {
		console.log('initialising artifact links macro');
		toggleUrlDisabled();
		$('#macro-param-override').change(toggleUrlDisabled);
		$('#macro-param-serverurl').change(updateArtifactLinksURL);
		console.log($('#macro-param-serverurl'));
		$('#macro-param-project').change(updateArtifactLinksURL);
		$('#macro-param-plan').change(updateArtifactLinksURL);
		$('#macro-param-count').change(updateArtifactLinksURL);
		console.log('initialised artifact links macro');
    };
	
    $(document).ready(function() {
	    initArtifactsLinkMacro();
	});
});
