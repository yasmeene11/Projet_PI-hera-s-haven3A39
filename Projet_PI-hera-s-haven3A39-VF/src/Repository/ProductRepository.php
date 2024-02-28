<?php

namespace App\Repository;

use App\Entity\Product;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Product>
 *
 * @method Product|null find($id, $lockMode = null, $lockVersion = null)
 * @method Product|null findOneBy(array $criteria, array $orderBy = null)
 * @method Product[]    findAll()
 * @method Product[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProductRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Product::class);
    }
    public function searchP($test)
    {
        return $this->createQueryBuilder('p')
            ->where('p.Product_Name LIKE :test')
            ->orWhere('p.Product_Label LIKE :test')
            ->orWhere('p.Product_Quantity LIKE :test')
            ->setParameter('test', '%'.$test.'%')
            ->getQuery()
            ->getResult();
    }
    public function findAllOrderedByCategory($productType = null)
    {
        $queryBuilder = $this->createQueryBuilder('p')
            ->leftJoin('p.Category_Key', 'c')
            ->orderBy('c.Product_Type', 'ASC');
    
        if ($productType !== null) {
            $queryBuilder->andWhere('c.Product_Type = :productType')
                ->setParameter('productType', $productType);
        }
    
        return $queryBuilder->getQuery()->getResult();
    }
    public function searchPF($searchValue)
    {
        // Implement your search logic here, for example:
        return $this->createQueryBuilder('p')
            ->andWhere('p.Product_Name LIKE :searchValue')
            ->setParameter('searchValue', '%' . $searchValue . '%')
            ->getQuery()
            ->getResult(\Doctrine\ORM\Query::HYDRATE_ARRAY); // Return results as an array
    }


//    /**
//     * @return Product[] Returns an array of Product objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('p.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Product
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
