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

        final EditText txt = (EditText) findViewById(R.id.txt1);
        //EditText txt2 = (EditText) findViewById(R.id.txt2);
        //EditText txt3 = (EditText) findViewById(R.id.txt3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cria(txt);

            }
        });


    }

    public void Cria(EditText text)
    {
        Document documento = new Document(PageSize.LETTER);
        String arquivoNome = "Meu arquivo COxa.pdf";
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
            String htmlToPDF = "<html><head></head><body><h1>"+text.getText()+"</h1><p>OI eu " +
                    "sou o paragrafo</p></body>/html>";
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
