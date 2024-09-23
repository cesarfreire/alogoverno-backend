package br.udesc.alogoverno.servicos;

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

    public MidiaServico() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "ACCESS_KEY",
                "SECRET_KEY"
        );
        this.s3Presigner = S3Presigner.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public URL gerarUrlAssinada(String bucketName, String objectKey) {
        PresignedPutObjectRequest requisicaoPreAssinada = s3Presigner.presignPutObject(
                builder -> builder.putObjectRequest(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build())
                        .signatureDuration(Duration.ofMinutes(5)));

        return requisicaoPreAssinada.url();
    }
}
