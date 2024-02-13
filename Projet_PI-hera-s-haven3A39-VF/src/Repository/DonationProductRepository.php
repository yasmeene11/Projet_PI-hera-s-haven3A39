<?php

namespace App\Repository;

use App\Entity\DonationProduct;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<DonationProduct>
 *
 * @method DonationProduct|null find($id, $lockMode = null, $lockVersion = null)
 * @method DonationProduct|null findOneBy(array $criteria, array $orderBy = null)
 * @method DonationProduct[]    findAll()
 * @method DonationProduct[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DonationProductRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, DonationProduct::class);
    }

//    /**
//     * @return DonationProduct[] Returns an array of DonationProduct objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('d.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?DonationProduct
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
