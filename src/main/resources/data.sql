-- Insert Foods
INSERT INTO
    foods(place_to_buy, description, food_name, price_amount, price_currency, category, image_url)
VALUES
    ('RESTAURANT',
     'Ez már nem étel, hanem életvitel!.',
     'Töltött káposzta',
     2700,
     'HUF',
     'MAIN_DISHES',
     'https://i.imgur.com/08KabXl.jpeg'
     ),
    ('RESTAURANT',
     'Ez nem csak egy gyros, ez egy ízbomba! A pita olyan puha, hogy megölel, a hús olyan fűszeres, hogy táncra perdít, a tzatziki pedig olyan fokhagymás, hogy még a vámpírok is tisztelettel távol maradnak. Egy igazi hős a tányérodon!',
     'Gyros tradícionálisan',
     3700,
     'HUF',
     'MAIN_DISHES',
     'https://i.imgur.com/R7iod68.jpeg'
     ),
    ('RESTAURANT',
     'A Húsleves készítése nem egyszerű feladat. A legenda szerint a tökéletes húsleves titka egy ősi recept, amelyet csak a legbölcsebb nagymamák ismernek. A receptet állítólag egy gyűrűbe vésték, amelyet a "Levesek Urának" nevezett szakács őriz. Aki megszerzi ezt a gyűrűt, az uralhatja a levesek világát – és minden vasárnapi ebédet.',
     'Húsleves',
     2000,
     'HUF',
     'SOUPS',
     'https://i.imgur.com/b2ylLK9.jpeg'
     ),
    ('RESTAURANT',
     'A gazpacho olyan, mint a nyár: könnyű, frissítő, és néha kicsit furcsa. De pont ezért szeretjük!🥵',
     'Gazpacho',
     2100,
     'HUF',
     'SOUPS',
     'https://i.imgur.com/ucnUmV3.jpeg'
     ),
    ('RESTAURANT',
     'A bécsi konyha közismert, 3-4 milliméter vastagságúra kivert, borjúhúsból készített, panírozott húsétele.',
     'Bécsi szelet burgonyapürével',
     2500,
     'HUF',
     'MAIN_DISHES',
     'https://i.imgur.com/ong3QL4.jpeg'
     ),
    ('RESTAURANT',
     'Népszerű szénsavas üdítőital, mely szinte a világ összes országában kapható.',
     'Coca-cola',
     450,
     'HUF',
     'DRINKS',
     'https://i.imgur.com/y1wTgOX.jpeg'
     ),
     ('RESTAURANT',
      'Természetes ásványvíz a szentkirályon található forrásból.',
      'Ásványvíz',
      350,
      'HUF',
      'DRINKS',
      'https://i.imgur.com/IsKKHzz.jpeg'
      ),
     ('RESTAURANT',
      'A híres gyűrűk ura c. regény inspirálta kürt alakú belül csupa csokis tekercs tejszínhabbal és gyümölcsökkel töltve. Gimli kedvence mióta egyszer megfújta.',
      'Helm kürtje',
      1750,
      'HUF',
      'DESSERTS',
      'https://i.imgur.com/miF42m9.jpeg'
      ),
    ('RESTAURANT',
     'Az ale típusú sörök felső erjedésű, könnyen elkészíthető sörök. Eredetük a korai időkre nyúlik vissza, szinte a legősibb sörök közvetlen leszármazottja.',
     'Ale',
     500,
     'HUF',
     'DRINKS',
     'https://i.imgur.com/QmSzdz2.jpeg'
     ),
    ('RESTAURANT',
     'A vikingek kedvence, a mézsör, kérhető hűtött vagy forró italként. Sajnáljuk, ha viking is vagy, nem adhatunk egy egész hordóval.',
     'Mézsör',
     650,
     'HUF',
     'DRINKS',
     'https://i.imgur.com/ZLaIrXz.jpeg'
     ),
    ('RESTAURANT',
     'Fa és más növények kérgéből készült tea. A házi sámánunk adta a receptet, állítása szerint ezer éve nagyot ment ez a tea. Széles körű gyógyhatásai vannak. Többféle ízben kapható.',
     'Kéreg tea',
     350,
     'HUF',
     'DRINKS',
     'https://i.imgur.com/Ry48iNi.jpeg'
     ),
    ('RESTAURANT',
     'A hobbitok, akik naponta legalább hét étkezést tartanak, a Pudingot a "Második Reggeli" szent desszertjének tartják. A Megyében minden évben megrendezik a "Nagy Pudingpróbát", ahol a legbátrabb hobbitok versenyeznek, hogy ki tudja a legtöbb Pudingot elfogyasztani anélkül, hogy elaludna vagy felrobbanna. (Spoiler: A Puding mindig győz.)',
     'Pudding',
     1550,
     'HUF',
     'DESSERTS',
     'https://i.imgur.com/VpHUHyc.jpeg'
     ),
    ('RESTAURANT',
     'Titkos erdei kódfeltörés eredménye – ropogósra hack-elt hal, amitől még a trollok is újraindulnak!',
     'Tündék hack-je',
     1500,
     'HUF',
     'MAIN_DISHES',
     'https://i.imgur.com/MG5U1A1.jpeg'
     ),
    ('RESTAURANT',
     'A magyar levesek királya, 100% gulyásból készült. (Hortobágyról hoztuk őket.)',
     'Gulyásleves',
     1000,
     'HUF',
     'SOUPS',
     'https://i.imgur.com/wjAzyfm.jpeg'
     ),
    ('RESTAURANT',
     'A fogadót kísértő hölgy szelleme nem volt hajlandó üzletünk kísértését abbahagyni, amíg e laktató és mennyei ételt el nem hozzuk a másvilágiaknak. Hidd el, inkább a pöri, mint a szellem néni.',
     'Elfsong Tavern pörkölt',
     2000,
     'HUF',
     'MAIN_DISHES',
     'https://i.imgur.com/4RCi4LR.jpeg'
     ),
    ('FANTASY_WORLD',
     'Egy adag 10 liter. Adj egy Deákot tesó!',
     'Mordori sör',
     20000,
     'HUF',
     'DRINKS',
     'https://i.imgur.com/w41qi1p.jpeg'
     );

-- Insert Ingredients
INSERT INTO
    ingredients(name)
VALUES
    ('Rízs'),               -- id - 1
    ('Sertéscomb'),         -- id - 2
    ('Vöröshagyma'),        -- id - 3
    ('Fokhagyma'),          -- id - 4
    ('Tojás'),              -- id - 5
    ('Kolbász'),            -- id - 6
    ('Oldalas'),            -- id - 7
    ('Savanyú káposzta'),   -- id - 8
    ('Fűszerek'),           -- id - 9
    ('Marhahús'),           -- id - 10
    ('Bárányhús'),          -- id - 11
    ('Burgonya'),           -- id - 12
    ('Joghurt'),            -- id - 13
    ('Paradicsom'),         -- id - 14
    ('Saláta'),             -- id - 15
    ('Lilakáposzta'),       -- id - 16
    ('Lilahagyma'),         -- id - 17
    ('Uborka'),             -- id - 18
    ('Répa'),               -- id - 19
    ('Zeller'),             -- id - 20
    ('Karalábé'),           -- id - 21
    ('Csirkehús'),          -- id - 22
    ('Cérnametélt'),        -- id - 23
    ('Petrezselyem'),       -- id - 24
    ('Paprika'),            -- id - 25
    ('Citromlé'),           -- id - 26
    ('Fehérborecet'),       -- id - 27
    ('Olívaolaj'),          -- id - 28
    ('Kenyér'),             -- id - 29
    ('Borjúhús'),           -- id - 30
    ('Liszt'),              -- id - 31
    ('Zsemlemorzsa'),       -- id - 32
    ('Tej'),                -- id - 33
    ('Cukor'),              -- id - 34
    ('Kukoricakeményítő'),  -- id - 35
    ('Sütőpor'),            -- id - 36
    ('Vanília cukor'),      -- id - 37
    ('Kakaópor'),           -- id - 38
    ('Tejszínhab'),         -- id - 39
    ('Áfonya'),             -- id - 40
    ('Eper'),               -- id - 41
    ('Banán'),              -- id - 42
    ('Komló'),              -- id - 43
    ('Élesztő'),            -- id - 44
    ('Méz'),                -- id - 45
    ('Víz'),                -- id - 46
    ('Fehér tölgy kéreg'),  -- id - 47
    ('Hal'),                -- id - 48
    ('Marhacsont'),         -- id - 49
    ('Paradicsompüré'),     -- id - 50
    ('Bacon'),              -- id - 51
    ('Sonka'),              -- id - 52
    ('Bab'),                -- id - 53
    ('Maláta ecet'),        -- id - 54
    ('Vörösbor ecet'),      -- id - 55
    ('Szauron búzája'),     -- id - 56
    ('Árpamaláta');         -- id - 57

-- Link Ingredients to Foods
INSERT INTO
    food_ingredients(food_id, ingredient_id)
VALUES
-- Töltött káposzta tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Rízs')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Sertéscomb')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Vöröshagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Fokhagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Tojás')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Kolbász')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Oldalas')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Savanyú káposzta')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),

-- Gyros tradícionálisan tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Marhahús')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Bárányhús')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Burgonya')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Joghurt')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Paradicsom')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Saláta')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Lilakáposzta')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Lilahagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Uborka')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM ingredients WHERE name = 'Fokhagyma')),

-- Húsleves tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Répa')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Zeller')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Karalábé')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Vöröshagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Csirkehús')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Cérnametélt')),
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM ingredients WHERE name = 'Petrezselyem')),

-- Gazpacho tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Paradicsom')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Uborka')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Paprika')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Vöröshagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Fokhagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Citromlé')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Fehérborecet')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Olívaolaj')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),
    ((SELECT id FROM foods WHERE food_name = 'Gazpacho'), (SELECT id FROM ingredients WHERE name = 'Kenyér')),

-- Bécsi szelet burgonyapürével tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Borjúhús')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Liszt')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Tojás')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Zsemlemorzsa')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Olívaolaj')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Burgonya')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM ingredients WHERE name = 'Tej')),

-- Coca-cola tartalmaz (nincs összetevő)

-- Ásványvíz tartalmaz (nincs összetevő)

-- Helm kürtje tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Sütőpor')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Vanília cukor')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Kakaópor')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Tejszínhab')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Áfonya')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Eper')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Banán')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Tojás')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Cukor')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Liszt')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Kukoricakeményítő')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM ingredients WHERE name = 'Tej')),

-- Ale tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Ale'), (SELECT id FROM ingredients WHERE name = 'Komló')),
    ((SELECT id FROM foods WHERE food_name = 'Ale'), (SELECT id FROM ingredients WHERE name = 'Árpamaláta')),

-- Mézsör tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Mézsör'), (SELECT id FROM ingredients WHERE name = 'Élesztő')),
    ((SELECT id FROM foods WHERE food_name = 'Mézsör'), (SELECT id FROM ingredients WHERE name = 'Méz')),
    ((SELECT id FROM foods WHERE food_name = 'Mézsör'), (SELECT id FROM ingredients WHERE name = 'Víz')),

-- Kéreg tea tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Kéreg tea'), (SELECT id FROM ingredients WHERE name = 'Fehér tölgy kéreg')),
    ((SELECT id FROM foods WHERE food_name = 'Kéreg tea'), (SELECT id FROM ingredients WHERE name = 'Víz')),
    ((SELECT id FROM foods WHERE food_name = 'Kéreg tea'), (SELECT id FROM ingredients WHERE name = 'Méz')),

-- Pudding tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Méz')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Tej')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Kukoricakeményítő')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Vanília cukor')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Kakaópor')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Áfonya')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Eper')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Banán')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM ingredients WHERE name = 'Tojás')),

-- Tündék hack-je tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Hal')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Répa')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Zeller')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Burgonya')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Paprika')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),

-- Gulyásleves tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Marhacsont')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Marhahús')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Paradicsompüré')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Répa')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Zeller')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Vöröshagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Fokhagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),

-- Elfsong Tavern pörkölt tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Bacon')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Sonka')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Marhahús')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Bab')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Burgonya')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Répa')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Paradicsom')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Fokhagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Vöröshagyma')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Petrezselyem')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Paprika')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Citromlé')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Olívaolaj')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Fűszerek')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Maláta ecet')),
    ((SELECT id FROM foods WHERE food_name = 'Elfsong Tavern pörkölt'), (SELECT id FROM ingredients WHERE name = 'Vörösbor ecet')),

-- Mordori sör tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Mordori sör'), (SELECT id FROM ingredients WHERE name = 'Szauron búzája'));

-- Insert Allergens
INSERT INTO
    allergens(name, icon_name)
VALUES
-- id - 1
    (
    'Glutént tartalmazó gabonafélék',
    'faWheatAwn'
    ),
-- id - 2
    (
    'Rákfélék és a belőlük készült termékek',
    'faShrimp'
    ),
-- id - 3
    (
    'Tojás és a belőle készült termékek',
    'faEgg'
    ),
-- id - 4
    (
    'Hal és a belőle készült termékek',
    'faFish'
    ),
-- id - 5
    (
    'Földimogyoró és a belőle készült termékek',
    'faPlateWheat'
    ),
-- id - 6
    (
    'Szójabab és a belőle készült termékek',
    'faSeedling'
    ),
-- id - 7
    (
    'Tej és az abból készült termékek',
    'faJar'
    ),
-- id - 8
    (
    'Diófélék és a belőlük készült termékek',
    'faBowlRice'
    ),
-- id - 9
    (
    'Zeller és a belőle készült termékek',
    'faCarrot'
    ),
-- id - 10
    (
    'Mustár és a belőle készült termékek',
    'faHotdog'
    ),
-- id - 11
    (
    'Szezámmag és a belőle készült termékek',
    'faLeaf'
    ),
-- id - 12
    (
    'Kén-dioxid és más szulfitok',
    'faVolcano'
    ),
-- id - 13
    (
    'Csillagfürt és a belőle készült termékek',
    'faPlantWilt'
    ),
-- id - 14
    (
    'Puhatestűek és a belőlük készült termékek',
    'faMound'
    );

-- Link Foods to Allergens
INSERT INTO
    food_allergens(food_id, allergen_id)
VALUES
-- Töltött káposzta tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM allergens WHERE name = 'Tojás és a belőle készült termékek')),
    ((SELECT id FROM foods WHERE food_name = 'Töltött káposzta'), (SELECT id FROM allergens WHERE name = 'Tej és az abból készült termékek')),

-- Gyros tradícionálisan tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Gyros tradícionálisan'), (SELECT id FROM allergens WHERE name = 'Tej és az abból készült termékek')),

-- Húsleves tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Húsleves'), (SELECT id FROM allergens WHERE name = 'Zeller és a belőle készült termékek')),

-- Gazpacho tartalmaz (nincs allergén)

-- Bécsi szelet burgonyapürével tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM allergens WHERE name = 'Tojás és a belőle készült termékek')),
    ((SELECT id FROM foods WHERE food_name = 'Bécsi szelet burgonyapürével'), (SELECT id FROM allergens WHERE name = 'Tej és az abból készült termékek')),

-- Coca-cola tartalmaz (nincs allergén)

-- Ásványvíz tartalmaz (nincs allergén)

-- Helm kürtje tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM allergens WHERE name = 'Tojás és a belőle készült termékek')),
    ((SELECT id FROM foods WHERE food_name = 'Helm kürtje'), (SELECT id FROM allergens WHERE name = 'Tej és az abból készült termékek')),

-- Ale tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Ale'), (SELECT id FROM allergens WHERE name = 'Glutént tartalmazó gabonafélék')),

-- Mézsör tartalmaz (nincs allergén)

-- Kéreg tea tartalmaz (nincs allergén)

-- Pudding tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM allergens WHERE name = 'Tojás és a belőle készült termékek')),
    ((SELECT id FROM foods WHERE food_name = 'Pudding'), (SELECT id FROM allergens WHERE name = 'Tej és az abból készült termékek')),

-- Tündék hack-je tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM allergens WHERE name = 'Hal és a belőle készült termékek')),
    ((SELECT id FROM foods WHERE food_name = 'Tündék hack-je'), (SELECT id FROM allergens WHERE name = 'Zeller és a belőle készült termékek')),

-- Gulyásleves tartalmaz
    ((SELECT id FROM foods WHERE food_name = 'Gulyásleves'), (SELECT id FROM allergens WHERE name = 'Zeller és a belőle készült termékek'));

-- Elfsong Tavern pörkölt tartalmaz (nincs allergén)

-- Mordori sör tartalmaz (nincs allergén)

-- ============================================================
-- Demo users, customers, and orders (dev seed data)
-- Passwords: admin → Admin@1234  |  demouser → Demo@1234
-- ============================================================

-- Insert demo users
INSERT INTO MY_USERS (my_username, password, email, role)
VALUES
    ('admin',    '$2a$10$0aN7YAsDgp/9opmDNhyJCu8o6qsJwtyihdxMuZXDCRrGJtk0F4eNi', 'admin@imaginebar.hu', 'ADMIN'),
    ('demouser', '$2a$10$92aP.oox2PrSOy2vkxiIHeU96kdchLoczXvfI.k2t7AbwwKMIjjsa', 'demouser@imaginebar.hu', 'USER');

-- Insert demo customers
INSERT INTO CUSTOMERS (firstname, lastname, phone_number, email,
    billing_zip_code, billing_city, billing_street, billing_street_number, billing_floor_door,
    default_shipping_zip_code, default_shipping_city, default_shipping_street, default_shipping_street_number, default_shipping_floor_door,
    myuser_id)
VALUES
    ('Adminisztrátor', 'Felhasználó', '+36301234567', 'admin@imaginebar.hu',
     '1011', 'Budapest', 'Fő utca', '1', null,
     '1011', 'Budapest', 'Fő utca', '1', null,
     (SELECT id FROM MY_USERS WHERE my_username = 'admin')),
    ('Demo', 'Felhasználó', '+36301234568', 'demouser@imaginebar.hu',
     '1051', 'Budapest', 'Vörösmarty tér', '7-8', '2/3',
     '1051', 'Budapest', 'Vörösmarty tér', '7-8', '2/3',
     (SELECT id FROM MY_USERS WHERE my_username = 'demouser'));

-- Insert default shipping addresses for demo customers
INSERT INTO SHIPPING_ADDRESSES (customer_id, zip_code, city, street, street_number, floor_door)
VALUES
    ((SELECT id FROM CUSTOMERS WHERE email = 'admin@imaginebar.hu'),    '1011', 'Budapest', 'Fő utca', '1', null),
    ((SELECT id FROM CUSTOMERS WHERE email = 'demouser@imaginebar.hu'), '1051', 'Budapest', 'Vörösmarty tér', '7-8', '2/3');

-- Insert demo order for demouser (2× Gulyásleves + 3× Ale = 3500 HUF, CARD payment)
WITH demo_order AS (
    INSERT INTO ORDERS (firstname, lastname, phone_number,
        shipping_zip_code, shipping_city, shipping_street, shipping_street_number, shipping_floor_door,
        total_cost_amount, total_cost_currency, payment_type, customer_id)
    VALUES (
        'Demo', 'Felhasználó', '+36301234568',
        '1051', 'Budapest', 'Vörösmarty tér', '7-8', '2/3',
        3500, 'HUF', 'CARD',
        (SELECT id FROM CUSTOMERS WHERE email = 'demouser@imaginebar.hu')
    )
    RETURNING id
)
INSERT INTO ORDER_ITEMS (quantity, order_item_price_amount, order_item_price_currency, food_id, order_id)
SELECT 2, 1000, 'HUF', (SELECT id FROM FOODS WHERE food_name = 'Gulyásleves'), id FROM demo_order
UNION ALL
SELECT 3,  500, 'HUF', (SELECT id FROM FOODS WHERE food_name = 'Ale'),         id FROM demo_order;
