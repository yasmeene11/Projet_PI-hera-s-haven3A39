<?php

namespace App\Form;

use App\Entity\Category;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FormCategoryType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('Product_Type')
            ->add('Product_Type', TextType::class, [
                'label' => 'Product_Type',
                'attr' => [
                    'placeholder' => 'Enter product type',
                ],
            ])
            ->add('Product_Source')
            ->add('Product_Source', TextType::class, [
                'label' => 'Product_Source',
                'attr' => [
                    'placeholder' => 'Enter product source',
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Category::class,
        ]);
    }
}
