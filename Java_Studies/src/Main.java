import br.com.studies.Java01Exercises;
import br.com.studies.infnet.*;

import java.math.BigDecimal;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        out.println(new Java01Exercises("abc").getTextA().getTextB().text);

        out.println("\n--- Exercício 01 ---");
        Produto cadeiraGamer = new Produto("Cadeira Gamer", Paths.get("cadeiras/gamer"), new BigDecimal("1000.00"));
        Produto notebookAvell = new Produto("Notebook Avell", Paths.get("notebooks/avell"), new BigDecimal("4000.00"));
        Produto livroHarryPotter = new Produto("Livro Harry Potter", Paths.get("livros/harry_potter"), new BigDecimal("50.00"));
        Produto pacoteImgNFTS = new Produto("Pacote de Imagens - NFT", Paths.get("nfts/pacote_imagens"), new BigDecimal("6000.00"));

        Cliente andre = new Cliente();
        Cliente camila = new Cliente();
        Cliente jonas = new Cliente();
        Cliente maria = new Cliente();

        andre.setNome("André");
        camila.setNome("Camila");
        jonas.setNome("Jonas");
        maria.setNome("Maria");

        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime ontem = hoje.minusDays(1);
        LocalDateTime mesPassado = hoje.minusMonths(1);
        LocalDateTime anoPassado = hoje.minusYears(1);
        LocalDateTime anoRetrasado = hoje.minusYears(2);
        LocalDateTime anoQueVem = hoje.plusYears(1);

        Pagamento pagamentoAndre = new Pagamento(List.of(cadeiraGamer, notebookAvell, livroHarryPotter, pacoteImgNFTS), mesPassado, andre);
        Pagamento pagamentoCamila = new Pagamento(List.of(notebookAvell, livroHarryPotter, pacoteImgNFTS), mesPassado, camila);
        Pagamento pagamentoJonas = new Pagamento(List.of(cadeiraGamer, notebookAvell, livroHarryPotter, pacoteImgNFTS, pacoteImgNFTS), ontem, jonas);
        Pagamento pagamentoMaria = new Pagamento(List.of(cadeiraGamer, notebookAvell), hoje, maria);

        List<Pagamento> pagamentos = Arrays.asList(pagamentoAndre, pagamentoCamila, pagamentoJonas, pagamentoMaria);
        pagamentos.forEach(p -> out.println(p.toString()));

        out.println("\n--- Exercício 02 ---");
        pagamentos.sort(Comparator.comparing(p -> p.getDataCompra()));
        Function<Pagamento, LocalDateTime> resgataDatasCompra = p -> p.getDataCompra();
        Comparator<Pagamento> comparator = Comparator.comparing(resgataDatasCompra);
        pagamentos.sort(comparator);
        out.println("--- Ordenação 01 ---");
        pagamentos.forEach(out::println);

        List<Pagamento> pagamentosOrdenadosPorDataCompra = pagamentos
            .stream()
            .sorted(
                Comparator.comparing(Pagamento::getDataCompra)
            )
            .toList();
        out.println("--- Ordenação 02 ---");
        pagamentosOrdenadosPorDataCompra.forEach(out::println);

        out.println("\n--- Exercício 03 ---");
        out.printf("Usando BigDecimal: %s%n", pagamentoAndre.somaPrecosProdutosPagtoUsandoBigDecimal());
        out.printf("Usando Optional: %s%n", pagamentoAndre.somaPrecosProdutosPagtoUsandoOptional());

        out.println("\n--- Exercício 04 ---");
        BigDecimal todosPagtosSomados = pagamentos.stream().map(Pagamento::somaPrecosProdutosPagtoUsandoBigDecimal).reduce(BigDecimal.ZERO, BigDecimal::add);
        out.printf("Somando todos os pagtos: %s%n", todosPagtosSomados);

        out.println("\n--- Exercício 05 ---");
        out.printf("Contando produtos vendidos para o Cliente Maria : %s%n", pagamentoMaria.getProdutos().size());
        out.printf("Contando produtos vendidos para o Cliente Jonas : %s%n", pagamentoJonas.getProdutos().size());
        out.printf("Contando produtos vendidos para o Cliente Andre : %s%n", pagamentoAndre.getProdutos().size());
        out.printf("Contando produtos vendidos para o Cliente Camila : %s%n", pagamentoCamila.getProdutos().size());

        OptionalInt contadorProdutosTotaisVendidos = pagamentos.stream().mapToInt(pagamento -> pagamento.getProdutos().size()).reduce(Integer::sum);
        out.printf("Contando total de produtos vendidos: %s%n", contadorProdutosTotaisVendidos.getAsInt());

        out.println("\n--- Exercício 06 ---");
        Cliente c1 = new Cliente();
        c1.setNome("Douglas Lima");
        Cliente c2 = new Cliente();
        c2.setNome("Lucas Santos");
        Produto prodCadeiraGamer = new Produto("Cadeira Gamer", Paths.get("cadeiras/gamer"), new BigDecimal("1000.00"));
        Produto pacoteDeImagens = new Produto("Pacote de Imagens - NFT", Paths.get("nfts/pacote_imagens"), new BigDecimal("6000.00"));
        HashMap<String, List<Produto>> clienteProdutos = new HashMap<>();
        clienteProdutos.put(c1.getNome(), List.of(prodCadeiraGamer, pacoteDeImagens));
        clienteProdutos.put(c2.getNome(), List.of(prodCadeiraGamer));
        clienteProdutos
            .entrySet()
            .stream()
            .map(
                e -> String.format(
                    "Nome do Cliente: %s | Item(ns): %s",
                    e.getKey(),
                    e.getValue()
                )
            )
            .forEach(out::println);
        //

        out.println("\n--- Exercício 07 ---");
        out.println("Sem ordenação - apenas impressão do map com as chaves e valores:");
        pagamentos.forEach(pagamento ->
            {
                HashMap<String, BigDecimal> mapClienteProdutos = new HashMap<>();
                BigDecimal somaProdutosComprados = pagamento.getProdutos().stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
                mapClienteProdutos.put(pagamento.getCliente().getNome(), somaProdutosComprados);
                mapClienteProdutos
                    .entrySet()
                    .stream()
                    .map(
                        e -> String.format(
                            "Nome do Cliente: %s | Item(ns): %s",
                            e.getKey(),
                            e.getValue()
                        )
                    )
                    .forEach(out::println);
                //
            }
        );

        Map<String, BigDecimal> mapClienteProdutosSemOrdem = new HashMap<>();
        pagamentos.forEach((Pagamento pagamento) ->
            {
                BigDecimal somaProdutosComprados = pagamento.getProdutos().stream().map(Produto::getPreco).reduce(BigDecimal.ZERO, BigDecimal::add);
                mapClienteProdutosSemOrdem.put(pagamento.getCliente().getNome(), somaProdutosComprados);
            }
        );

        LinkedHashMap<String, BigDecimal> mapClienteProdutosOrdenado = mapClienteProdutosSemOrdem
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new
                )
            );
        //
        out.printf("Ordenando pelo maior valor gasto em compras: %s%n", mapClienteProdutosOrdenado);

        out.println("Apresentando o cliente que gastou o maior valor em compras:");
        Map.Entry<String, BigDecimal> clienteQueGastouMais = mapClienteProdutosOrdenado.entrySet().stream().findFirst().get();
        out.printf(
            "Nome do Cliente: %s | Valor em Compra(s): %s%n",
            clienteQueGastouMais.getKey(),
            clienteQueGastouMais.getValue()
        );

        Map<Cliente, List<List<Produto>>> clienteParaProdutos = pagamentos.stream().collect(Collectors.groupingBy(
           Pagamento::getCliente, Collectors.mapping(Pagamento::getProdutos, Collectors.toList())));
        out.println(clienteParaProdutos);

        Map<Cliente, List<Produto>> flatMapClienteParaProdutos = clienteParaProdutos.entrySet().stream().collect(Collectors.toMap(
           Map.Entry::getKey, e -> e.getValue().stream().flatMap(List::stream).collect(Collectors.toList())));
        out.println(flatMapClienteParaProdutos);

        Function<Pagamento, BigDecimal> reducingFunction = p -> p.getProdutos()
           .stream()
           .map(Produto::getPreco)
           .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Cliente, BigDecimal> topClientes = pagamentos
           .stream()
           .collect(
               Collectors.groupingBy(
                   Pagamento::getCliente,
                   Collectors.reducing(
                       BigDecimal.ZERO,
                       reducingFunction,
                       BigDecimal::add
                   )
               )
           );
        //

        topClientes.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(out::println);

        List<Map.Entry<Cliente, BigDecimal>> ordenaClientePorGasto = topClientes.entrySet().stream().sorted(
           Map.Entry.comparingByValue(
               Comparator.reverseOrder()
           )
        ).toList();
        out.printf("Cliente com o maior gasto: %s%n", ordenaClientePorGasto.stream().findFirst().get());

        out.println("\n--- Exercício 08 ---");
        BigDecimal somaPagtosPorPeriodoEspecifico = pagamentos
           .stream()
           .filter(pagto -> pagto.getDataCompra().equals(mesPassado))
           .map(Pagamento::somaPrecosProdutosPagtoUsandoBigDecimal)
           .reduce(BigDecimal.ZERO, BigDecimal::add);
        out.printf("Clientes com pagamento do mês passado: %s%n", somaPagtosPorPeriodoEspecifico);

        out.println("\n--- Exercício 09 ---");
        Assinatura assinaturaAndre = new Assinatura(new BigDecimal("99.98"), new Periodo(ontem, hoje), Plano.TRIMESTRAL, andre, true);
        Assinatura assinaturaCamila = new Assinatura(new BigDecimal("99.98"), new Periodo(anoRetrasado, anoPassado), Plano.SEMESTRAL , camila, false);
        Assinatura assinaturaJonas = new Assinatura(new BigDecimal("99.98"), new Periodo(anoRetrasado), Plano.ANUAL, jonas, false);
        Assinatura assinaturaMaria = new Assinatura(new BigDecimal("99.98"), new Periodo(hoje, anoQueVem), Plano.SEMESTRAL, maria, false);

        List<Assinatura> assinaturas = List.of(assinaturaAndre, assinaturaCamila, assinaturaJonas, assinaturaMaria);
        assinaturas.forEach(out::println);

        Periodo p1 = new Periodo(ontem, hoje);
        Periodo p2 = new Periodo(hoje, anoQueVem);
        out.println(p1.getBegin() + " " + p1.getEnd());
        out.println(p2.getBegin() + " " + p2.getEnd());

        out.println(assinaturaCamila.getPeriodo().getBegin());
        out.println(assinaturaCamila.getPeriodo().getEnd().orElse(hoje));

        out.println("\n--- Exercício 10 ---");
        long periodoAssinaturaAndre = ChronoUnit.MONTHS.between(assinaturaAndre.getPeriodo().getBegin(), assinaturaAndre.getPeriodo().getEnd().orElse(hoje));
        out.printf("O período de assinatura da André é de %s meses %n", periodoAssinaturaAndre);

        long periodoAssinaturaMaria = ChronoUnit.MONTHS.between(assinaturaMaria.getPeriodo().getBegin(), assinaturaMaria.getPeriodo().getEnd().orElseGet(() -> hoje));
        out.printf("O período de assinatura da Camila é de %s meses %n", periodoAssinaturaMaria);

        out.println("\n--- Exercício 11 ---");
        assinaturas
            .stream()
            .map(Assinatura::getPeriodo)
            .mapToLong(
                p -> ChronoUnit.MONTHS.between(
                    p.getBegin(),
                    p.getEnd().orElse(hoje)
                )
            )
            .forEach(contagemPeriodo -> {
                out.printf(
                    "Contando o tempo de assinatura: %s%n",
                    contagemPeriodo
                );
            });
        //

        assinaturas
            .stream()
            .collect(
                Collectors.toMap(
                    a -> a.getCliente().getNome(),
                    a -> ChronoUnit.MONTHS.between(
                        a.getPeriodo().getBegin(),
                        a.getPeriodo().getEnd().orElse(hoje)
                    )
                )
            )
            .entrySet()
            .stream()
            .map(
                e -> String.format(
                    "Nome do Cliente: %s | Tempo da assinatura em mês/meses: %s",
                    e.getKey(),
                    e.getValue()
                )
            ).forEach(out::println);
        //

        out.println("\n--- Exercício 12 ---");

        out.println("\n--- Lista 02 ---");
        assinaturas
            .stream()
            .collect(
                Collectors.toMap(
                    a -> a.getCliente().getNome(),
                    a -> a.somaAssinaturas(hoje)
                )
            )
            .entrySet()
            .stream()
            .map(
                e -> String.format(
                    "Nome do Cliente: %s | Total pago das assinaturas: R$ %s",
                    e.getKey(),
                    e.getValue()
                )
            ).forEach(out::println);
        //
        
        out.println();
        assinaturas.forEach(assinatura -> {
            out.print(assinatura.getCliente().bloqueiaCompra(assinatura, pagamentos));
        });
    }
}