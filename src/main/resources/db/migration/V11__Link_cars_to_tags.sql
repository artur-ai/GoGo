INSERT INTO car_tags (car_id, tag_id)
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'Fabia'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model = 'Fabia'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Механіка') FROM cars WHERE model = 'Fabia'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'R2'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model = 'R2'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model = 'R2'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'Polo'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model = 'Polo'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Механіка') FROM cars WHERE model = 'Polo'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'Fiesta'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model = 'Fiesta'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model = 'Fiesta'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Дизель') FROM cars WHERE model = 'Jetta'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Преміум') FROM cars WHERE model = 'Jetta'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model = 'Jetta'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model = 'Rapid'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Механіка') FROM cars WHERE model = 'Rapid'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'Rapid'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Електро') FROM cars WHERE model = 'Leaf'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model = 'Leaf'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Економ') FROM cars WHERE model = 'Leaf'

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Преміум') FROM cars WHERE model IN ('Octavia', 'A4', '3 Series', 'Camry')
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бензин') FROM cars WHERE model IN ('Octavia', 'A4', '3 Series', 'Camry')
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model IN ('Octavia', 'A4', '3 Series', 'Camry')

UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Бус') FROM cars WHERE model = 'H-1'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Дизель') FROM cars WHERE model = 'H-1'
UNION ALL
SELECT id, (SELECT id FROM tags WHERE name = 'Автомат') FROM cars WHERE model = 'H-1';