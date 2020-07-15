package letscode.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import letscode.sarafan.domain.Message;
import letscode.sarafan.domain.Views;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){

        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message getOne(@PathVariable("id") Long id){
        Message message = messageRepo.findById(id).get();
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable ("id") Long id,
            @RequestBody Message message
    ){
        Message messageFromDB = messageRepo.findById(id).get();
        BeanUtils.copyProperties(message, messageFromDB, "id");
        return messageRepo.save(messageFromDB);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){

        Message message = messageRepo.findById(id).get();
        messageRepo.delete(message);

    }
}
