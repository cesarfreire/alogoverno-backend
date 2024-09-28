package br.udesc.alogoverno.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;


@Service
public class MidiaServico {
    private final S3Presigner s3Presigner;

    @Autowired
    private Environment env;

    public MidiaServico(Environment env) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                env.getProperty("alogoverno.app.aws.credentials.accessKey"),
                env.getProperty("alogoverno.app.aws.credentials.secretKey")
        );
        this.s3Presigner = S3Presigner.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public URL gerarUrlAssinada(String objectKey) {
        PresignedPutObjectRequest requisicaoPreAssinada = s3Presigner.presignPutObject(
                builder -> builder.putObjectRequest(PutObjectRequest.builder()
                        .bucket(env.getProperty("alogoverno.app.aws.s3.bucket"))
                        .key(objectKey)
                        .build())
                        .signatureDuration(Duration.ofMinutes(5)));

        return requisicaoPreAssinada.url();
    }
}
