//package controller;
//
//import com.leilao.model.Leilao;
//import com.leilao.model.Lance;
//import com.leilao.service.LeilaoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/leiloes")
//public class LeilaoController {
//
//    @Autowired
//    private LeilaoService leilaoService;
//
//    @PostMapping
//    public ResponseEntity<Leilao> criarLeilao(@RequestBody Leilao leilao) {
//        return ResponseEntity.ok(leilaoService.criarLeilao(leilao));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Leilao> buscarLeilao(@PathVariable Long id) {
//        return leilaoService.buscarLeilao(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping("/{leilaoId}/lances")
//    public ResponseEntity<?> registrarLance(
//            @PathVariable Long leilaoId,
//            @RequestBody Lance lance) {
//        boolean sucesso = leilaoService.registrarLance(leilaoId, lance);
//        if (sucesso) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }
//}

package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeilaoController {

    @GetMapping("/hello")
    public String hello() {
        return "API REST funcionando!";
    }
}
