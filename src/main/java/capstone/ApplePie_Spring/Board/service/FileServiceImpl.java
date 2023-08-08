package capstone.ApplePie_Spring.Board.service;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.domain.File;
import capstone.ApplePie_Spring.Board.dto.FileDto;
import capstone.ApplePie_Spring.Board.repository.FileRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseException;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

//    @Value("${upload.path}")
//    private String uploadPath;

    @Value("${app.firebase-file}")
    private String path;

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;

    private static String downPath_front = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/";
    private static String downPath_back = "?alt=media";

    public final FileRepository fileRepository;
    private static final Integer STATUS = 1;


    public String uploadFiles(MultipartFile file, String name) throws IOException, FirebaseException {
        Bucket bucket = StorageClient
                .getInstance()
                .bucket(firebaseBucket);
        InputStream content = new ByteArrayInputStream(file.getBytes());
        Blob blob = bucket.create(name.toString(), content, file.getContentType());
        return blob.getMediaLink();
    }

    @Override
    public File save(Board board, int fileNumber, MultipartFile multipartFile) {
        try {

            /*
            java.io.File saveFile = new java.io.File(uploadPath + name + '.' + extension);
            String url = saveFile.getPath();
            //System.out.println("saveFile.getPath() = " + saveFile.getPath());
            multipartFile.transferTo(saveFile);
             */

            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String name = FilenameUtils.getBaseName(multipartFile.getName())
                    + board.getId() + '-' + fileNumber;
            String link = uploadFiles(multipartFile, name); // 반환 값 미사용
            String url = downPath_front+name+downPath_back;
            long size = url.getBytes().length;

            FileDto fileDto = FileDto.builder()
                    .name(name)
                    .size(size)
                    .extension(extension)
                    .number(fileNumber)
                    .url(downPath_front+name+downPath_back)
                    .build();
            return fileRepository.save(fileDto.toFile(board));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<File> findOne(Long boardId, int number) {
        return fileRepository.findByBoardIdAndNumberAndStatus(boardId, number, STATUS);
    }


    @Override
    public List<File> findByBoardId(Long boardId) {
        return fileRepository.findAllByBoardIdAndStatus(boardId, STATUS);
    }
}
