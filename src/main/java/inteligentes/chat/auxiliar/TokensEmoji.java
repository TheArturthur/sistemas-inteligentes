package inteligentes.chat.auxiliar;

import java.util.LinkedList;

import jade.core.Agent;

public class TokensEmoji {
	

	public static final String comando1 = "/sonrisa\n";
	public static final String comando2 = "/alegre\n";
	public static final String comando3 = "/triste\n";
	public static final String comando4 = "/sorpresa\n";
	public static final String comando5 = "/hola\n";
	public static final String comando6 = "/llorar\n";
	public static final String comando7 = "/callado\n";
	public static final String comando8 = "/avergonzado\n";
	public static final String comando9 = "/hambriento\n";
	public static final String comando10 = "/corazon\n";
	public static final String comando11 =  "/pillo\n";
	public static final String comando12 = "/amor\n";
	public static final String comando13 = "/ipod\n";
	public static final String comando14 = "/felicesfiestas\n";
	public static final String comando15 = "/felizcumple\n";
	public static final String comando16 = "/tequiero\n";
	public static final String comando17 = "/casa\n";
	
	public static final String special1 = "/comandos\n";
	public static final String special2 = "/list\n";
	
	public static String getComandos() {
		StringBuilder builder = new StringBuilder();
		LinkedList<String> comandos = new LinkedList<String>();
		
		comandos.add(comando1);
		comandos.add(comando2);
		comandos.add(comando3);
		comandos.add(comando4);
		comandos.add(comando5);
		comandos.add(comando6);
		comandos.add(comando7);
		comandos.add(comando8);
		comandos.add(comando9);
		comandos.add(comando10);
		comandos.add(comando11);
		comandos.add(comando12);
		comandos.add(comando13);
		comandos.add(comando14);
		comandos.add(comando15);
		comandos.add(comando16);
		comandos.add(comando17);

		for (int i = 0; i < comandos.size(); i++) {
			builder.append(comandos.get(i));
		}
		
		return builder.toString();
	}
	
	
	public static final String comando1value = ":-)\n";
	public static final String comando2value = ":-D\n";
	public static final String comando3value = ":-(\n";
	public static final String comando4value = ":-O\n";
	public static final String comando5value = "\\O.\n";
	public static final String comando6value = ":'(\n";
	public static final String comando7value = ":-#\n";
	public static final String comando8value = ":-$\n";
	public static final String comando9value = ":-P\n";
	public static final String comando10value = "<3\n";
	public static final String comando11value = ";-)\n";
	
	
	
	public static final String comando12value = 
			"                   _  _\n" + 
			"                 ( \\/ )\n" + 
			"          .---.   \\  /   .-\"-. \n" + 
			"         /   6_6   \\/   / 4 4 \\\n" + 
			"         \\_  (__\\       \\_ v _/\n" + 
			"         //   \\\\        //   \\\\\n" + 
			"        ((     ))      ((     ))\n" + 
			"  =======\"\"===\"\"========\"\"===\"\"=======\n" + 
			"           |||            |||\n" + 
			"            |              |\n";
	
	public static final String comando13value = 
			"╔═══╗ ♪\n" + 
			"║███║ ♫\n" + 
			"║ (●) ♫\n" + 
			"╚═══╝♪♪\n";
	
	public static final String comando14value = 
			"Feliz★* 。 • ˚ ˚ ˛ ˚ ˛ •\n" + 
			"•。★Navidad★ 。* 。\n" + 
			"° 。 ° ˛˚˛ * _Π_____*。*˚\n" + 
			"˚ ˛ •˛•˚ */______/~＼。˚ ˚ ˛\n" + 
			"˚ ˛ •˛• ˚ ｜ 田田 ｜門｜ ˚\n" + 
			"Un feliz año nuevo\n";
	
	public static final String comando15value = 
			"                *,,,,,*,,,,,*\n" + 
			"                '0,,,,0,,,,0\n" + 
			"               _||___||___||_\n" + 
			"        '*,,{,,,,,,,,,,,,,,,,,,,},*\n" + 
			"        0,,{/\\/\\/\\/\\/\\/\\/\\/\\/\\/},'0     \n" + 
			"        _||_{________\"________}_||_  \n" + 
			"     {/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/}\n" + 
			"     {,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,}\n" + 
			"     {/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/}\n" + 
			"     {_______________\"_______________}\n" + 
			"	    Muchas Felicidades\n";
	
	public static final String comando16value = 
			"                        .-\"\"\"-.    .-\"\"\"-.\n" + 
			"                       /       '..'       \\\n" + 
			"                .-\"\"\"-;    .-\"\"\"-.         |\n" + 
			"        _      /       `..'       \\        |\n" + 
			"     .-' /    |                    |      /  /////\n" + 
			"    <   (==========)    T U        | (======<<<<<\n" + 
			"     '-._\\     \\        Y         /    /`    \\\\\\\\\\\n" + 
			"                \\      Y O      /   /'\n" + 
			"                 `\\            /'  /'\n" + 
			"                   `\\        /'`\\/'\n" + 
			"                     `\\    /'\n" + 
			"                       `\\/'\n";
	
	
	public static final String comando17value =
			"                 /`\\\n" + 
			"           _n   /` , `\\\n" + 
			"          |  |/` /` `\\ `\\---------,________\\ \n" + 
			"          | /` /;-----;\\ `\\        `\\      `\\\n" + 
			"          /` /` ||_|_|| `\\ `\\        `\\      `\\\n" + 
			"        /` /`   ||_|_||   `\\ `\\________`\\______`\\\n" + 
			"      /`_/`     '-----'     `\\_`\\-------;      |\"\n" + 
			"      \"| .---.   .---.   .---. |  .--.  | .--. |\n" + 
			"       | |T_T|   |T_T|   |T_T| |  |LI|  | |LI| |\n" + 
			"       | |L_I|   |L_I|   |L_I| |  |LI|  | |LI| |\n" + 
			"       | '---'   '---' _.--._' |  '--'  | '--' |\n" + 
			"      _|____________,-\" _.._ \"-;________|______|_\n" + 
			"     /_______________,-\" __ \"-._________T________\\\n" + 
			"     \"||   .----.   ||  |LI|  ||  .--.  | .--. ||\"\n" + 
			"      ||   |LILI|   ||  | .|  ||  |LI|  | |LI| ||\n" + 
			"      ||   |LILI|  _JL_ |  | _JL_ |LI|  | |LI| ||\n" + 
			"      ||   '----'  |\"\"|_|__|_|\"\"| '--'  | '--' ||\n" + 
			"      ||TTTTTTTTTTT|  |======|  |TTTTTTTTTTTTTTTT\n" + 
			"      ||||||||||||||  |======|  |||||||||||||||||\n" + 
			"     ^^^^^^^^^^^^^^^^^^      ^^^^^^^^^^^^^^^^^^^^^\n";
	
	
//	public static final String comando1value = "\\O.\n";
//	public static final String comando2value = "\\O/\n";
//	public static final String comando3value = "\\1.\n";
//	public static final String comando4value = "\\1/\n";
//	public static final String comando5value = "\\O/\n";
//	public static final String comando6value = "\\O/\n";
//	public static final String comando7value = "\\O/\n";
//	public static final String comando8value = "\\O/\n";
//	public static final String comando9value = "\\O/\n";
//	public static final String comando10value = "\\O/\n";



}