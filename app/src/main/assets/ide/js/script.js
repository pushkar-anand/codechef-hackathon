var editor = ace.edit("editor");

var resetOnChange = true;
var languageSelected = "C++14";
//Language Boilerplate Codes
var langBoilerplate = {};
langBoilerplate['C'] = "#include <stdio.h>\nint main(void) {\n	// your code goes here\n	return 0;\n}\n";
langBoilerplate['C++14'] = "#include <iostream>\nusing namespace std;\n\nint main() {\n	// your code goes here\n	return 0;\n}\n";
langBoilerplate['C#'] = "using System;\n\npublic class Test\n{\n	public static void Main()\n	{\n		// your code goes here\n	}\n}\n";
langBoilerplate['CLOJ'] = "; your code goes here";
langBoilerplate['HASK'] = "main = -- your code goes here";
langBoilerplate['JAVA'] = "public class TestDriver {\n    public static void main(String[] args) {\n        // Your code goes here\n    }\n}";
langBoilerplate['JS'] = "importPackage(java.io);\nimportPackage(java.lang);\n\n// your code goes here\n";
langBoilerplate['NODEJS'] = "process.stdin.resume();\nprocess.stdin.setEncoding('utf8');\n\n// your code goes here";
langBoilerplate['PERL'] = "#!/usr/bin/perl\n# your code goes here\n";
langBoilerplate['PERL6'] = "#!/usr/bin/perl\n# your code goes here\n";
langBoilerplate['PHP'] = "<?php\n\n// your code goes here\n";
langBoilerplate['PYTH'] = "def main():\n    # Your code goes here\n\nif __name__ == \"__main__\":\n    main()";
langBoilerplate['PYPY'] = "def main():\n    # Your code goes here\n\nif __name__ == \"__main__\":\n    main()";
langBoilerplate['PYTH 3.6'] = "def main():\n    # Your code goes here\n\nif __name__ == \"__main__\":\n    main()";
langBoilerplate['RUBY'] = "# your code goes here";
langBoilerplate['RUST'] = "fn main() {\n    // The statements here will be executed when the compiled binary is called\n\n    // Print text to the console\n    println!(\"Hello World!\");\n}\n";
langBoilerplate['SCALA'] = "object Main extends App {\n	// your code goes here\n}\n";

var langMap = {};

langMap['C'] = "c_cpp";
langMap['C++14'] = 'c_cpp';
langMap['C#'] = "csharp";
langMap['CLOJ'] = "clojure";
langMap['HASK'] = "haskell";
langMap['JAVA'] = 'java';
langMap['JS'] = 'javascript';
langMap['NODEJS'] = 'javascript';
langMap['PERL'] = 'perl';
langMap['PERL6'] = 'perl';
langMap['PHP'] = 'php';
langMap['PYTH'] = 'python';
langMap['PYPY'] = 'python';
langMap['PYTH 3.6'] = 'python';
langMap['RUBY'] = 'ruby';
langMap['RUST'] = 'rust';
langMap['SCALA'] = 'scala';


$(document).ready(function () {

    ace.require("ace/ext/language_tools");
    editor.getSession().setUseWorker(false);
    editor.setTheme("ace/theme/monokai");
    editor.session.setUseWrapMode(true);
    editor.session.setMode("ace/mode/c_cpp");
    ace.config.set("basePath", "src");
    ace.require("ace/ext/language_tools");
    editor.setOptions({
        enableBasicAutocompletion: true,
        enableSnippets: true,
        enableLiveAutocompletion: true
    });
    editor.setValue(langBoilerplate[languageSelected]);

    $("#lang").change(function () {
        languageSelected = $("#lang").val();

        // update the language (mode) for the editor

        var langToMod = "ace/mode/" + langMap[languageSelected];
        editor.getSession().setMode(langToMod);

        if (resetOnChange) {
            editor.setValue(langBoilerplate[languageSelected]);
        }
    });
});