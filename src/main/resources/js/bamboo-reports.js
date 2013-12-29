var jsOverrides = {
    "fields" : {
        "string" : function(params, options){ 
            return AJS.MacroBrowser.ParameterFields["string"](params, options);
        }
    }
};
//AJS.MacroBrowser.setMacroJsOverride("bamboo artifact links", jsOverrides);