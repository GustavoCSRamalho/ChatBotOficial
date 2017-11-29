package projetobot;



import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import dp.ParagraphVectorsClassifierExample;

//acessar o bot father
//crir um novo bot
//pegar o token dado pelo bot father e inserir na linha 22

public class Bot {

	public static void main(String[] args) throws Exception {
		ParagraphVectorsClassifierExample app = new ParagraphVectorsClassifierExample();
		//Cria��o do objeto bot com as informa��es de acesso
		TelegramBot bot = TelegramBotAdapter.build("465080232:AAFxZ2NKpqtg-7Ss7iiRTSVK75hiDeFxel4");
		BotDAO botDAO = new BotDAO();
		String conteudo;

		//objeto respons�vel por receber as mensagens
		GetUpdatesResponse updatesResponse;
		//objeto respons�vel por gerenciar o envio de respostas
		SendResponse sendResponse;
		//objeto respons�vel por gerenciar o envio de a��es do chat
		BaseResponse baseResponse;
		
		//controle de off-set, isto �, a partir deste ID ser� lido as mensagens pendentes na fila
		int m=0;
		
		//loop infinito pode ser alterado por algum timer de intervalo curto
		while (true){
		
			//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
			
			//lista de mensagens
			List<Update> updates = updatesResponse.updates();

			//an�lise de cada a��o da mensagem
			for (Update update : updates) {	
				
				//atualiza��o do off-set
				m = update.updateId()+1;
				
				//System.out.println("Recebendo mensagem:"+ update.message().text());
				
				//envio de "Escrevendo" antes de enviar a resposta
				baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
				//verifica��o de a��o de chat foi enviada com sucesso
				//System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
				
				//envio da mensagem de resposta
//				sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"N�o entendi..."));
//				sendResponse = bot.execute(new SendMessage(update.message().chat().id(),update.message().text()));
				sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
						"Executando : "+update.message().text()));
				conteudo = update.message().text();
//				botDAO.comando(update.message().text());
				if(conteudo.equalsIgnoreCase("yes")) {
					FileWriter arq = new FileWriter("/home/gustavo/eclipse-workspace/Teste123/src/main/resources/paravec/unlabeled/assuntorecebido/texto.txt");
					arq.write(conteudo);
					arq.close();
					app.makeParagraphVectors();
					app.checkUnlabeledData();
					System.out.println("Criei o arquivo");
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Mensagem no terminal!"));
					
				}else {
					botDAO.comando(conteudo);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), botDAO.mensagem()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Deseja procurar "
							+ "algum assunto relacionado a isso?\n Se sim, digite yes, se nao, continue com os"
							+ "comandos!"));
					
				}
				//verifica��o de mensagem enviada com sucesso
				//System.out.println("Mensagem Enviada?" +sendResponse.isOk());
				
			}

		}

	}

}
