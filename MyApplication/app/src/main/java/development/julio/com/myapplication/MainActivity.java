package development.julio.com.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;


public class MainActivity extends AppCompatActivity {
    private static final String pasta = "development.julio.com.myapplication"; //Nombre Carpeta App
    private static final String nome = "Meus arquivos"; //Generados



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.botao);

        final EditText txt1 = (EditText) findViewById(R.id.txt1);
        final EditText txt2 = (EditText) findViewById(R.id.txt2);
        final EditText txt3 = (EditText) findViewById(R.id.txt3);
        final EditText txt4 = (EditText) findViewById(R.id.txt4);
        final EditText txt5 = (EditText) findViewById(R.id.txt5);
        final EditText txt6 = (EditText) findViewById(R.id.txt6);
        final EditText txt7 = (EditText) findViewById(R.id.txt7);
        final EditText txt8 = (EditText) findViewById(R.id.txt8);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cria(txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8);
            }
        });


    }

    public void Cria(EditText numero, EditText paciente, EditText tutor, EditText tel, EditText proced, EditText afeccao, EditText vet, EditText anestesista)
    {
        Document documento = new Document(PageSize.LETTER);
        String arquivoNome = "Meu arquivo.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        File arq = new File(tarjetaSD + File.separator + pasta);

        if(!arq.exists())
        {
            arq.mkdir();
        }

        File outro = new File(arq.getPath() + File.separator + nome);

        if(!outro.exists())
        {
            outro.mkdir();
        }

        String nomeCompleto = Environment.getExternalStorageDirectory() + File.separator + pasta +
                File.separator + nome + File.separator + arquivoNome;
        File outputfile = new File(nomeCompleto);

        if(outputfile.exists())
        {
            outputfile.delete();
        }

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(documento,new FileOutputStream(nomeCompleto));

            documento.open();
            documento.addAuthor("Coxa");
            documento.addCreator("Coxinha JR.");
            documento.addSubject("To fazendo um documento");
            documento.addCreationDate();
            documento.addTitle("Titulo lalala");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmlToPDF = "<html> <head> <style> td { padding: 8px; padding-right: 15px; } tr { margin: 0 auto; } </style> </head> <body><table border="+1+"> <tr> <td>Número:"+numero.getText()+"</td> <td>Paciente: "+paciente.getText()+"</td> </tr> <tr> <td>Tutor: "+tutor.getText()+"</td> <td>Telefone: "+tel.getText()+"</td> </tr> <tr> <td>Procedimento: "+proced.getText()+"</td> <td>Afecção: "+afeccao.getText()+"</td> </tr> <tr> <td>Vet. Responsável: "+vet.getText()+"</td> <td>Anestesista: "+anestesista.getText()+"</td> </tr> </table></body>\n" +
                    "</html>";

            /*"<html><head></head><body><h1>"+text.getText()+"</h1><p>OI eu " +
                    "sou o paragrafo</p><table border="+"1"+"><tr><td>linha 1, célula 1</td>" +
                    "<td>linha 1, célulal 2</td></tr></table></body>/html>"*/

            try {
                worker.parseXHtml(pdfWriter,documento, new StringReader(htmlToPDF));
                documento.close();
                Toast.makeText(this,"PDF esta gerado",Toast.LENGTH_LONG).show();
                mostraPDF(nomeCompleto,this);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void mostraPDF(String arquivo, Context context)
    {
        Toast.makeText(context,"Lendo arquivo",Toast.LENGTH_LONG).show();
        File file = new File(arquivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        try
        {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(context,"Nao tem um app para abrir este arquivo",Toast.LENGTH_LONG).show();
        }


    }

}
