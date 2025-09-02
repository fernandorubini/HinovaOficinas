Hinova Oficinas (Android)


Aplicativo Android de prova técnica com autenticação, listagem de Oficinas, Indicação de amigo e gerenciamento de sessão do usuário com DataStore.

Sumário

Funcionalidades

Arquitetura & Stack

Estrutura de pastas

Screenshots

Configuração & Execução

Endpoints

GET /Api/Oficina

POST /Api/Indicacao

Sessão do usuário

Erros & Logs

DI com Koin

Navegação

UI/UX

Itens do escopo

Próximos passos

Como adicionar/atualizar screenshots

Licença

Funcionalidades

Login (ActivityLogin)

Campos: CPF e Senha.

Demo:

CPF: 78885983073

Senha: 37038958887

Persiste usuário em DataStore e navega para a tela principal.

Tela Principal (ActivityPrincipal)

TabLayout com:

Oficinas: lista consumida da API (apenas ativas).

Indicação: formulário que consome /Api/Indicacao.

Ordens de Serviço: card no rodapé (atalho/placeholder — fora do escopo principal).

Oficinas

RecyclerView + OficinasAdapter.

Clique abre OficinaDetailsActivity exibindo:

Foto (base64), Nome, Telefone (opcional), Descrição Curta, Endereço.

(Bônus) Avaliação do usuário, quando presente.

Indicação (ActivityIndication)

Formulário: Nome, Telefone (máscara BR), E-mail do amigo.

Envia envelope exigido pela API com dados do associado vindo do DataStore.

Arquitetura & Stack

Kotlin, Material 3, AndroidX View System

RecyclerView (lista de Oficinas)

DataStore (Preferences) para sessão (SessionManager)

Retrofit + OkHttp + Gson (Rede)

Koin (Injeção de dependência)

Coroutines/Flows (assíncrono)

Suporte a cleartext HTTP (API do teste é http://)

Estrutura de pastas

app/src/main/java/br/com/fernandodev/hinovaoficinas/
├─ HinovaApp.kt
├─ core/
│  └─ network/
│     ├─ HinovaApi.kt
│     └─ HinovaWebRepository.kt
├─ data/
│  ├─ net/models/
│  │  ├─ OficinasResponseDto.kt
│  │  └─ IndicationModels.kt
│  └─ session/SessionManager.kt
├─ di/Modules.kt
├─ domain/model/Oficina.kt
└─ ui/
├─ login/ActivityLogin.kt
├─ principal/ActivityPrincipal.kt
├─ tabs/
│  ├─ CarRepairFragment.kt
│  └─ OficinasAdapter.kt
├─ details/OficinaDetailsActivity.kt
└─ indication/ActivityIndication.kt

Configuração & Execução

1. Requisitos

Android Studio Koala+

JDK 17+

Android 8+ (API 26+)

2. Clonar & abrir

3. Base URL (di/Modules.kt)
   private const val BASE_URL =
   "http://app.hinovamobile.com.br/ProvaConhecimentoWebApi/"

   
4. Permissões / Network

Adicione ao **AndroidManifest.xml**:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<application
    android:name=".HinovaApp"
    android:usesCleartextTraffic="true"
    android:networkSecurityConfig="@xml/network_security_config"
    ... >
    ...
</application>

5. Executar

Run: o módulo app.
Login com as credenciais de demonstração (acima).

Endpoints
GET /Api/Oficina

Exemplo do escopo:

/Api/Oficina?codigoAssociacao=601&cpfAssociado=""


O repositório tenta variações automaticamente para contornar ambientes com HTTP 404:

/Api/Oficina e /Api/Oficina/

/api/Oficina e /api/Oficina/

Parâmetro cpfAssociado como "" (aspas) e como string vazia ""

DTO → Domínio
OficinasResponseDto.kt mapeia para domain/model/Oficina.kt via OficinaDto.toDomain().

POST /Api/Indicacao

Body enviado (envelope exigido):

{
"Indicacao": {
"CodigoAssociacao": 601,
"DataCriacao": "yyyy-MM-dd",
"CpfAssociado": "…",
"EmailAssociado": "…",
"NomeAssociado": "…",
"TelefoneAssociado": "…",
"PlacaVeiculoAssociado": null,
"NomeAmigo": "…",
"TelefoneAmigo": "…",
"EmailAmigo": "…",
"Observacao": null
},
"Remetente": "email@associado.com",
"Copias": []
}

Resposta esperada:

{
"RetornoErro": { "retornoErro": null },
"Sucesso": "Indicacao enviada com sucesso!"
}

Sessão do usuário

data/session/SessionManager.kt (DataStore Preferences)

API:

setUser(context, user: LoginUser?)
getUser(context): LoginUser?
userFlow(context): Flow<LoginUser?>
clear(context)

DI com Koin

class HinovaApp : Application() {
override fun onCreate() {
super.onCreate()
startKoin {
androidContext(this@HinovaApp)
modules(appModules)
}
}
}
di/Modules.kt
networkModule: OkHttp (logging), Retrofit, HinovaApi, HinovaWebRepository
viewModelModule: OficinasViewModel
demais módulos existentes do projeto

Navegação

Launcher → ActivityLogin

Pós-login → ActivityPrincipal (tabs Oficinas e Indicação)

Detalhes → OficinaDetailsActivity

OS → Card no rodapé da guia Oficinas (placeholder/atalho)

UI/UX

Título “Hinova Oficinas” com logo no topo.

Em Oficinas, o card de Ordens de Serviço fica ao final da tela, sem sobrepor a lista.

Itens do escopo

Login + persistência de sessão (DataStore)
TabLayout com Oficinas e Indicação
Consumo da API de Oficinas (com fallback de rota/param)
Formulário de Indicação consumindo /Api/Indicacao
Detalhes da Oficina na ordem solicitada
Tratamento de estados (loading/empty/error)

Próximos passos / TODO

Paginação/busca de oficinas
Cache offline (Room)
Testes unitários para mapeadores e repository
Placeholders e melhor UX para imagem base64
Implementar tela real do Gerenciador de OS
i18n (pt-BR/en-US)

Como adicionar/atualizar screenshots

Crie a pasta: docs/screenshots/
Salve as imagens com estes nomes (ou edite os paths no README):
login.png
principal.png
indicacao.png
detalhes.png

Faça commit:
git add docs/screenshots/*.png README.md
git commit -m "docs: add screenshots"

Licença

Este projeto é destinado à avaliação técnica. Opcionalmente, adicione a licença (ex.: MIT).